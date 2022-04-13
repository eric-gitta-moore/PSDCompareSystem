package com.example.newcompare.controller;


import com.example.newcompare.common.utils.FileUtil;
import com.example.newcompare.common.utils.Result;
import com.example.newcompare.entity.*;
import com.example.newcompare.service.FileService;
import com.example.newcompare.service.OrderLogService;
import com.example.newcompare.service.TaskGroupService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import com.example.newcompare.common.utils.Result;
import com.example.newcompare.entity.File;
import com.example.newcompare.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author nosgua
 * @since 2022-04-10
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TaskGroupService taskGroupService;

    @Autowired
    private FileService fileService;

    @Autowired
    private OrderLogService orderLogService;

    @ApiOperation("李超===>上传图片，文件＋taskID")
    @PostMapping("/uploadFile")
    public Result uploadFile(MultipartFile[] files, Integer taskId) throws IOException {
        if(files != null)
        {
            //获取图片的分辨率和大小信息
            List<FileInformation> fileInformations = FileUtil.getInformation(files);
            //判断上传的图片分辨率是否相同，不同则直接结束上传，因为分辨率不同无法进行对比
            FileInformation fileInformation = fileInformations.get(0);
            for(int i = 1; i <= fileInformations.size(); i++)
            {
                if(!fileInformation.getSize().equals(fileInformations.get(i).getSize()))
                {
                    return Result.fail(400,"图片分辨率不统一，请更换文件再试！",null);
                }
            }
            //用taskId查到task_grpou中对应的那一条数据，用这条数据的workcode去调文件上传接口
            TaskGroup taskGroup = taskGroupService.selectById(taskId);
            //用于存放file对象在数据库中的ID
            ArrayList<Integer> fileId = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                //生成fileCode
                Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                String fileCode = formatter.format(currentTime);
                Random random = new Random();

                File file = new File();
                file.setFilecode(fileCode + random.nextInt(99));
                file.setName(files[i].getName());
                file.setTaskId(taskId);
                file.setResolution(fileInformations.get(i).getResolution());
                file.setSize(fileInformations.get(i).getSize());
                file.setCreateTime(LocalDateTime.now());

                //将文件信息保存进数据库，并返回其在数据库中的ID
                fileId.add(fileService.insertFile(file));
            }

            BigDecimal b = new BigDecimal("100");

            OrderLog orderLog =
                    new OrderLog().setCreateTime(LocalDateTime.now()).
                            setStatus("create").setFee(b).setOutTradeId( UUID.randomUUID().toString()).
                            setTitle("test").setSerialNumber(UUID.randomUUID().toString()).setDeleted(0).
                            setFirstId(fileId.get(0)).setSecondId(fileId.get(1)).setSize(fileInformations.get(0).getSize()).
                            setResolution(fileInformations.get(1).getSize()).setTaskId(taskId);
            orderLogService.insertOrderLog(orderLog);
            //将文件上传服务器
            for (int i = 0; i < files.length; i++) {
                //在项目路径下创建临时文件夹
                java.io.File localFile = new java.io.File("localFile");
                if(!localFile.exists()){
                    localFile.createNewFile();
                }
                try {
                    files[i].transferTo(localFile);
                    FileSystemResource fileSystemResource = new FileSystemResource(localFile);
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                    MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
                    request.add("file", fileSystemResource);
                    request.add("workcode", taskGroup.getWorkCode());
                    request.add("filecode", fileService.seleceFileById(fileId.get(i)));
                    HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(request, httpHeaders);
                    ResponseResult responseResult =
                            restTemplate.postForObject("http://139.9.203.100:9721/cadpare/upload", httpEntity, ResponseResult.class);
                    Integer errcode = responseResult.getErrcode();
                    if(errcode != 0)
                    {
                        return Result.fail(400,"文件上传失败，请重试！",null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.fail(400,"文件上传失败，请重试！",null);
                } finally {
                    localFile.delete();
                }

            }
            return Result.success(200,"文件上传成功",null);
        }

        return Result.fail(400,"文件上传失败",null);

    }


    /**
     * 获取一个或者多个文件的信息
     */
    @ApiOperation("获取一个或者多个文件的信息")
    @GetMapping("/getFiles/{groupId}")
    public Result getFiles(@PathVariable("groupId") Integer groupId){
        ArrayList<File> fileMessages = fileService.queryById(groupId);
        return Result.success(fileMessages);
    }

    @ApiOperation("左呈祥===>获取对比之后的的文件的url        id : order_log的id")
    @GetMapping("/getUrl/{id}")
    public Result getUrlById(@PathVariable("id") Integer id){
        String url = fileService.getUrlById(id);
        Map<String,Object> map=new HashMap<>();
        if(url==null){
            map.put("url","");
            return Result.fail(404,"此id不存在或已被删除",map);
        }else if("".equals(url)){
            map.put("url","");
            return Result.success(200,"此id暂无url",map);
        }else {
            map.put("url",url);
            return Result.success(200,"获取url成功",map);
        }
    }

}
