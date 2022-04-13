package com.example.newcompare.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("task_group")
public class TaskGroup {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 工作码
     */
    private String workCode;

    /**
     * 任务组名
     */
    private String name;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @TableLogic//逻辑删除字段  1删除  0未删除
    private Integer deleted;

    /**
     *
     */
    @TableField(exist = false)
    private List<OrderLog> orders;
}
