package com.example.newcompare.service.impl;

import com.example.newcompare.entity.File;
import com.example.newcompare.mapper.FileMapper;
import com.example.newcompare.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
