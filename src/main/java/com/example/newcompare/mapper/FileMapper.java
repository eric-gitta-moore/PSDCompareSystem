package com.example.newcompare.mapper;

import com.example.newcompare.entity.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author nosgua
 * @since 2022-04-10
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {

    ArrayList<File> queryFiles(@Param("groupId") Integer groupId);

}
