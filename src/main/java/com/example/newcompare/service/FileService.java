package com.example.newcompare.service;

import com.example.newcompare.common.utils.Result;
import com.example.newcompare.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newcompare.entity.OrderLog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nosgua
 * @since 2022-04-10
 */
public interface FileService extends IService<File> {

    List<OrderLog> getAllSendId(Integer[] id);


    boolean backZip(List<OrderLog> list) throws Exception;

}
