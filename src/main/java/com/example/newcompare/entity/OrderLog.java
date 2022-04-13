package com.example.newcompare.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class OrderLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 支付状态('unpaid':未支付,'create':创建,'cancal':取消支付,'complete':完成)
     */
    @TableField("status")
    private String status;

    /**
     * 应付金额
     */
    @TableField("fee")
    private BigDecimal fee;

    /**
     * 商户订单ID
     */
    @TableField("out_trade_id")
    private String outTradeId;

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

    @TableField(exist = false)
    private List<File> files;

    /*public OrderLog(String status, BigDecimal fee, String outTradeId, String title, String serialNumber, LocalDateTime createTime, String url, Integer deleted, Integer firstId, Integer secondId, String size, String resolution, Integer taskId) {
        this.status = status;
        this.fee = fee;
        this.outTradeId = outTradeId;
        this.title = title;
        this.serialNumber = serialNumber;
        this.createTime = createTime;
        this.url = url;
        this.deleted = deleted;
        this.firstId = firstId;
        this.secondId = secondId;
        this.size = size;
        this.resolution = resolution;
        this.taskId = taskId;
    }*/
}
