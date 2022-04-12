package com.example.newcompare.controller;


import com.example.newcompare.common.utils.Result;
import com.example.newcompare.entity.File;
import com.example.newcompare.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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


}
