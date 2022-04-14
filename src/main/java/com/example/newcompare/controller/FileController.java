package com.example.newcompare.controller;


import com.example.newcompare.common.utils.Result;
import com.example.newcompare.common.utils.ZipUntils;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.service.FileService;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
    FileService fileService;

    /**
     *
     * @return 文件
     */
     @GetMapping("/download")
    public void fileDownload(Integer[] id, HttpServletResponse response) throws Exception {

         List<OrderLog> allSendId = fileService.getAllSendId(id);

          boolean iu= fileService.backZip(allSendId);
       //  response.setContentType("application/octet-stream");

         if(iu==true){
            //读，写
             FileInputStream in=null;

             ServletOutputStream out = null;
            try{
                in=new FileInputStream("src/main/resources/img.zip");
                out = response.getOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }catch (Exception o){
                o.printStackTrace();
            }finally {

               in.close();
               out.close();

            }

            //* return Result.success();*//*

         }


          ZipUntils.deleteDir("src/main/resources/img");
         ZipUntils.dalete1("src/main/resources/img.zip");



         /*  return Result.fail("下载失败");*/
     }


}
