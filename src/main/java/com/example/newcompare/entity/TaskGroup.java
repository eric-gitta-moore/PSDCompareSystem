package com.example.newcompare.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("task_group")
public class TaskGroup {
    /**
     * id
     */
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
     *
     */
    @TableField(exist = false)
    private List<OrderLog> orders;
}
