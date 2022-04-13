package com.example.newcompare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.newcompare.entity.File;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.mapper.FileMapper;
import com.example.newcompare.mapper.OrderLogMapper;
import com.example.newcompare.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nosgua
 * @since 2022-04-10
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

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
    public String getUrlById(Integer id) {
        LambdaQueryWrapper<OrderLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(id!=null,OrderLog::getId,id);
        Integer count = orderLogMapper.selectCount(queryWrapper);
        String url = fileMapper.getUrlById(id);
        if(count>0){
            return url!=null?url:"";
        }else {
            return null;
        }
    }
}
