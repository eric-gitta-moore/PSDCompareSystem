package com.example.newcompare.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 支付订单表
 * </p>
 *
 * @author nosgua
 * @since 2022-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_log")
public class OrderLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对比状态 complete 已完成  incomplete 未完成
     */
    @TableField("status")
    private String status;

    /**
     * 应付金额
     */
    @TableField("fee")
    private BigDecimal fee;

    /**
     * 任务码
     */
    @TableField("work_code")
    private String workCode;

    /**
     * 对比组名称
     */

    @TableField("title")
    private String title;

    /**
     * 流水号
     */
    @TableField("serial_number")
    private String serialNumber;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;


    /**
     * 对比之后的文件的位置
     */
    @TableField("url")
    private String url;

    @TableLogic//逻辑删除字段  1删除  0未删除
    private Integer deleted;

    /**
     * 第一张文件的id
     */
    @TableField("first_id")
    private Integer firstId;

    /**
     * 第二张文件的id
     */
    @TableField("second_id")
    private Integer secondId;

    /**
     * 文件大小
     */
    @TableField("size")
    private String size;

    /**
     * 图片分辨率
     */
    @TableField("resolution")
    private String resolution;

    /**
     * 任务组id
     */
    @TableField("task_id")
    private Integer taskId;

    /**
     * 对比结果
     */
    private String result;

    /**
     * 对比后文件名
     */
    private String fileName;

    @TableField(exist = false)
    private List<File> files;


}
