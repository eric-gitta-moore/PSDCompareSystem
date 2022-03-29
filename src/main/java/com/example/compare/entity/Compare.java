package com.example.compare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("compare")
public class Compare {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 工作码
     */
    @TableField("work_code")
    private String workcode;

    /**
     * 订单id
     */
    @TableField("order_id")
    private Integer orderId;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

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
}
