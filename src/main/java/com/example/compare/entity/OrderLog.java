package com.example.compare.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 支付订单表
 * </p>
 *
 * @author nosgua
 * @since 2022-03-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
     * 支付状态
     */
    private String status;

    /**
     * 应付金额
     */
    private BigDecimal fee;

    /**
     * 商户订单ID
     */
    private String outTradeId;

    /**
     * 订单名称
     */
    private String title;

}
