package com.example.newcompare.controller;


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
@Api(tags = "File")
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

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
