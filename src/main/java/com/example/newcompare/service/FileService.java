package com.example.newcompare.service;

import com.example.newcompare.common.utils.Result;
import com.example.newcompare.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
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

    String getUrlById(Integer id);
}
