package com.example.compare.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.compare.entity.Compare;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CompareService extends IService<Compare> {
    String getOrderIdById(Integer id);


    /**
     * 保存compare对象进compare表
     * @param compare
     * @return
     */
    public Integer saveCompare(Compare compare);
    /**
     * 保存CompareLog信息
     *
     * @param compareLog
     * @return
     */
    public Integer saveCompareLog(Compare compareLog);

    /**
     * 历史记录分页查询
     *
     * @return
     */
    Page<Compare> search(@Param("Page") Page<Compare> Page, @Param("keyWords") String keyWords, @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 查询单条记录
     * @param compareId
     * @return
     */
    Compare searchOne(Integer compareId);
    /**
     * 多表删除
     *
     * @return
     */
    int allDelete(int orderId);

    /**
     * 根据id获取数据
     *
     * @return
     */
    Compare select(int id);

    /**
     * 获取所有数据
     *
     * @return
     */
    List<Compare> allSelect();


    /**
     *  修改对比状态
     */
    public boolean updateCompareStatus(Integer id);

    /**
     * 根据serialNumber获取数据
     *
     * @return
     */
    Compare bySerialNumber(String serialNumber);

    /**
     * 获取记录总数
     * @return
     */
    String getTotalRows();
}
