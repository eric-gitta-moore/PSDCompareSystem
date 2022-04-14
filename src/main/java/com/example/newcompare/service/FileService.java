package com.example.newcompare.service;

import com.example.newcompare.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
