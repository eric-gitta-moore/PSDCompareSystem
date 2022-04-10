package com.example.newcompare.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
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
     * 支付状态('unpaid':未支付,'overtime':超时,'cancal':取消支付,'complete':完成)
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

    /**
     * 流水号
     */
    private String serialNumber;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * compare自增id
     */
    private Integer compareId;

    /**
     * 对比之后的文件的位置
     */
    private String url;


}
