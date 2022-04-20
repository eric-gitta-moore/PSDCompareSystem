package com.example.newcompare.controller;


import com.example.newcompare.common.utils.*;
import com.example.newcompare.service.FileService;
import com.example.newcompare.service.OrderLogService;
import com.example.newcompare.service.TaskGroupService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.newcompare.common.utils.Result;
import com.example.newcompare.entity.OrderLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author nosgua
 * @since 2022-04-10
 */
@RestController
@RequestMapping("/file")
public class FileController {


    @Autowired
    private FileService fileService;



    /**
     * @return 文件
     */
    @ApiOperation("肖恒宇====>多文件下载")
    @GetMapping("/download")
    public void fileDownload(Integer[] id, HttpServletResponse response) throws Exception {
        String path = "/tmp/template/"+UUID.randomUUID();
        List<OrderLog> allSendId = fileService.getAllSendId(id);

        boolean iu = fileService.backZip(allSendId,path);
        //  response.setContentType("application/octet-stream");

        if (iu == true) {
            //读，写
            FileInputStream in = null;

            ServletOutputStream out = null;
            try {
                in = new FileInputStream(path+".zip");
                out = response.getOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } catch (Exception o) {
                o.printStackTrace();
            } finally {

                in.close();
                out.close();

            }

            //* return Result.success();*//*

        }


        ZipUntils.deleteDir(path);
        new File(path+".zip").delete();





        /*  return Result.fail("下载失败");*/
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile [] file1, MultipartFile [] file2, Integer taskId) throws IOException
    {

        return fileService.upload(file1,file2,taskId);
    }

}
