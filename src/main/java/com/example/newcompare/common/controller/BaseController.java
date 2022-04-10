package com.example.newcompare.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.HashMap;
import java.util.Map;

/**
 * controller层公共抽取方法
 */

public class BaseController {

    /**
     * 获取分页后的数据
     * @param page
     * @return
     */
    public Map<String, Object> getData(IPage<?> page) {
        Map<String, Object> data = new HashMap<>();
        //总数
        data.put("rows", page.getRecords());
        //当前页数据
        data.put("total", page.getTotal());
        return data;
    }

}
