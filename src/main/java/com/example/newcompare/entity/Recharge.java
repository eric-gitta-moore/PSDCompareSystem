package com.example.newcompare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 充值记录，方便调用支付接口
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("recharge")
public class Recharge {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号
     */
    private String outTradeNo;

    /**
     * 充值金额
     */
    private String fee;

    /**
     * 标题
     */
    private String title;

    /**
     *  unpaid 未支付
     *  complete 完成
     *  fail 失败
     */
    private String status;
}
