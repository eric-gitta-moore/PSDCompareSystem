package com.example.newcompare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author nosgua
 * @since 2022-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件码
     */
    private String filecode;

    /**
     * orderId
     */
    private Integer orderId;

    @TableLogic//逻辑删除字段  1删除  0未删除
    private Integer deleted;

    /**
     * 文件名
     */
    private String name;

    /**
     * 图片分辨率
     */
    private String resolution;

    /**
     * 任务组id
     */
    private Integer taskId;

}
