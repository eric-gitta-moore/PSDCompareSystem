package com.example.compare.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.compare.entity.Compare;
import com.example.compare.mapper.CompareMapper;
import com.example.compare.service.CompareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CompareServiceImpl extends ServiceImpl<CompareMapper,Compare> implements CompareService {

    @Autowired
    CompareMapper mapper;
    @Override
    public String getOrderIdById(Integer id) {
        return mapper.getOrderIdByid(id);
    }

    @Resource
    private CompareMapper compareMapper;

    @Override
    public Integer saveCompare(Compare compare) {
        compareMapper.insert(compare);
        Integer id = compare.getId();
        return id;
    }

    @Override
    public boolean updateCompareStatus(Integer id) {
        UpdateWrapper<Compare> q1=new UpdateWrapper<>();
        q1.eq("order_id",id);
        q1.set("status","未完成");
        int update = mapper.update(null, q1);
        if(update==1){
            return true;
        }
            return false;

    }

    @Override
    public Integer saveCompareLog(Compare compareLog) {
        mapper.insert(compareLog);
        Integer id = compareLog.getId();
        return id;
    }

    @Override
    public List<Compare> search(String keywords, String startTime, String endTime, int index, int maxPage) {
        return mapper.search(keywords,startTime,endTime,index,maxPage);
    }

    @Override
    public Compare searchOne(Integer compareId) {
        return mapper.searchOne(compareId);
    }

    @Override
    public int allDelete(int orderId) {
        return mapper.allDelete(orderId);
    }

    @Override
    public Compare select(int id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Compare> allSelect() {
        return mapper.allSelect();
    }

    @Override
    public Compare bySerialNumber(String serialNumber) {
        return mapper.bySerialNumber(serialNumber);
    }
}
