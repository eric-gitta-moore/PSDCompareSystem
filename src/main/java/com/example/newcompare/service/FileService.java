package com.example.newcompare.service;

import com.example.newcompare.common.utils.Result;
import com.example.newcompare.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newcompare.entity.OrderLog;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author nosgua
 * @since 2022-04-10
 */
public interface FileService extends IService<File> {

    /**
     * 查询文件信息
     */
    ArrayList<File> queryById(Integer groupId);

    Map<String, Object> getUrlById(Integer id);

    /**
     * \
     * 保存数据进file表
     *
     * @param file
     * @return
     */
    Integer insertFile(File file);

    /**
     * \根据fileID查询file表中的数据
     *
     * @param fileId
     * @return
     */
    String seleceFileById(Integer fileId);

    List<OrderLog> getAllSendId(Integer[] id);


    boolean backZip(List<OrderLog> list,String path) throws Exception;

    Result upload(MultipartFile[] file1, MultipartFile[] file2, Integer taskId) throws IOException;
}
