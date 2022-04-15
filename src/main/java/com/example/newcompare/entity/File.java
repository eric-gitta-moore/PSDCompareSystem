package com.example.newcompare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
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



    @TableLogic//逻辑删除字段  1删除  0未删除
    private Integer deleted;



    /**
     * 文件名

     */
    private String name;



    /**
     * 任务组id
     */
    private Integer taskId;

    /**
     * 文件大小
     */
    private String size;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * OrderId
     */
    @TableField(exist = false)
    private Integer orderId;
}
