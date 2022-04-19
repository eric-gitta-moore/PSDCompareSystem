package com.example.newcompare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.newcompare.common.utils.FileDownloadUtil;
import com.example.newcompare.common.utils.FileUtil;
import com.example.newcompare.common.utils.Result;
import com.example.newcompare.common.utils.ThreadLocalUtil;
import com.example.newcompare.common.utils.ZipUntils;
import com.example.newcompare.entity.*;
import com.example.newcompare.mapper.FileMapper;
import com.example.newcompare.mapper.OrderLogMapper;
import com.example.newcompare.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newcompare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.newcompare.service.OrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import java.util.ArrayList;
import java.util.*;

import java.util.ArrayList;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author nosgua
 * @since 2022-04-10
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {



    @Autowired
    private FileMapper fileMapper;


    @Override
    public Integer insertFile(File file) {
        fileMapper.insert(file);
        Integer id = file.getId();
        return id;
    }

    @Override
    public String seleceFileById(Integer fileId) {
        File file = fileMapper.selectById(fileId);
        String filecode = file.getFilecode();
        return filecode;
    }

    @Override
    public ArrayList<File> queryById(Integer groupId) {
        ArrayList<File> fileMesseges = fileMapper.queryFiles(groupId);
        return fileMesseges;
    }

    @Override
    public Map<String, Object> getUrlById(Integer id) {
        LambdaQueryWrapper<OrderLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(id != null, OrderLog::getId, id);
        return fileMapper.getUrlById(id);
    }

    @Autowired
    private OrderLogService orderLogService;
    /**
     *
     * @return   订单表的集合
     */
    @Override
    public List<OrderLog> getAllSendId( Integer[] id) {
        QueryWrapper<OrderLog> q1=new QueryWrapper<>();
       q1.in("id",id);
        return orderLogService.list(q1);
    }


    /**
     *
     * @param list 订单表集合
     * @return 数据结果
     */
    @Override
    public boolean backZip(List<OrderLog> list) throws Exception {
        //不要放在项目里，设置一个绝对路径常量
        UUID uuid = UUID.randomUUID();
        ThreadLocalUtil.saveUser(uuid.toString());
        java.io.File file=new java.io.File("/"+uuid+"/img");//?
        file.mkdirs();

        for(OrderLog orderLog:list){
            if("complete".equals(orderLog.getStatus())){
                String url = orderLog.getUrl();
                if(url!=null && !"".equals(url)){
                    HttpURLConnection conn = null;
                    InputStream inputStream = null;
                    FileOutputStream e1=null;
                    try{
                        URL url1 = new URL(" http://"+url);
                          conn = (HttpURLConnection)url1.openConnection();
                           conn.connect();
                        //得到输入流
                         inputStream = conn.getInputStream();

                        e1=new FileOutputStream("/"+uuid+"/img/"+orderLog.getId()+".png");

                         byte[] bys=new byte[1024];
                         int len;
                         while((len=inputStream.read(bys))!=-1){
                              e1.write(bys,0,len);
                         }
                    }catch (Exception o){
                        o.printStackTrace();
                    }finally{
                        inputStream.close();
                        e1.close();
                        conn.disconnect();
                    }

                }else{
                    return false;
                }

            }
        }

        ZipUntils.getZip("/"+uuid+"/img");

        return true;

    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Override
    public Result upload(MultipartFile[] file1, MultipartFile[] file2, Integer taskId) throws IOException {
        if(file1 != null && file2 != null)
        {
            //用于生成文件码
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            Random random = new Random();

            //计算组文件的大小和分辨率
            List<FileInformation> fileInformations1 = FileUtil.getInformation(file1);
            List<FileInformation> fileInformations2 = FileUtil.getInformation(file2);
            //用于存放订单信息
            List<OrderLog> orderLogList = new ArrayList<>();
            User user = new User();
            int num;
            for(int i = 0; i < file1.length && i < file2.length; i++)
            {



                File file_1 = new File().setFilecode(formatter.format(new Date())+random.nextInt(99))
                        .setDeleted(0).setName(file1[i].getName()).setSize(fileInformations1.get(i).getSize()).setTaskId(taskId).setCreateTime(LocalDateTime.now());
                File file_2 = new File().setFilecode(formatter.format(new Date())+random.nextInt(99))
                        .setDeleted(0).setName(file2[i].getName()).setSize(fileInformations2.get(i).getSize()).setTaskId(taskId).setCreateTime(LocalDateTime.now());

                //添加需要对比的两文件的信息进数据库file表
                Integer fileId1 = insertFile(file_1);
                Integer fileId2 = insertFile(file_2);

                //生成工作码
                String workCode = UUID.randomUUID().toString();

                //上传需要对比的两张图片
                Boolean uploadFileStatus = uploadFile(file1[i], workCode, file_1.getFilecode());
                Boolean uploadFileStatus1 = uploadFile(file2[i], workCode, file_2.getFilecode());

                synchronized (user)
                {
                    if(userService.getBalance(1) < 100)
                    {
                        num = file1.length - orderLogList.size();
                        return Result.fail(400,"账户余额不足，请及时充值, 启动对比成功数："+orderLogList.size()+"\t启动对比失败数："+num,orderLogList);
                    }
                    //启动两张图片的对比任务
                    Boolean startCompareStatus = startCompare(workCode, file_1.getFilecode(), file_2.getFilecode());

                    if (uploadFileStatus && uploadFileStatus1 && startCompareStatus && userService.getBalance(1) >= 100)
                    {
                        String url = "http://139.9.203.100:9721/cadpare/status?workcode="+workCode;
                        BigDecimal b = new BigDecimal("100");
                        OrderLog orderLog =
                                new OrderLog().setCreateTime(LocalDateTime.now()).
                                        setStatus("incomplete").setFee(b).setWorkCode(workCode).
                                        setTitle("test").setSerialNumber(UUID.randomUUID().toString()).setDeleted(0).
                                        setFirstId(fileId1).setSecondId(fileId2).setSize(fileInformations1.get(i).getSize()).
                                        setResolution(fileInformations2.get(i).getSize()).setTaskId(taskId).setUrl(url);
                        orderLogService.insertOrderLog(orderLog);
                        orderLogList.add(orderLog);
                        Float balance = userService.getBalance(1);
                        balance -= 100;
                        user.setBalance(balance);
                        userService.updataUser(user);
                    }
                }

            }
            //计算对比失败组数
            num = file1.length - orderLogList.size();
            return Result.success(200,"上传成功，对比成功数:"+orderLogList.size()+"对比失败数："+num,orderLogList);
        }
        return Result.fail(400,"文件上传失败，请重试！",null);
    }

    /**
     * 上传文件方法
     * @param file
     * @param workCode
     * @param fileCode
     * @return
     * @throws IOException
     */
    public Boolean uploadFile(MultipartFile file, String workCode, String fileCode) throws IOException {
            //在项目路径下创建临时文件夹
            java.io.File localFile = new java.io.File("localFile");
            if (!localFile.exists()) {
                localFile.createNewFile();
            }
            try {
                file.transferTo(localFile);
                FileSystemResource fileSystemResource = new FileSystemResource(localFile);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
                request.add("file", fileSystemResource);
                request.add("workcode", workCode);
                request.add("filecode", fileCode);
                HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(request, httpHeaders);
                ResponseResult responseResult =
                        restTemplate.postForObject("http://139.9.203.100:9721/cadpare/upload", httpEntity, ResponseResult.class);
                Integer errcode = responseResult.getErrcode();
                if (errcode == 0) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                localFile.delete();
            }
            return false;
        }

    /**
     * 启动对比方法
     * @param workCode
     * @param fileCode1
     * @param fileCode2
     * @return
     */
        public Boolean startCompare(String workCode, String fileCode1, String fileCode2)
        {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            Map<String,Object> request=new HashMap<>();
            request.put("workcode", workCode);
            request.put("pair", fileCode1+":"+fileCode2);
            HttpEntity<Map<String,Object>> httpEntity=new HttpEntity<>(request,httpHeaders);
            ResponseResult responseResult =
                    restTemplate.postForObject("http://139.9.203.100:9721/cadpare/start", httpEntity, ResponseResult.class);
            Integer errcode = responseResult.getErrcode();
            if(errcode == 0)
            {
                return true;
            }
            return false;
        }
}
