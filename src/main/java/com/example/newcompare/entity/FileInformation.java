package com.example.newcompare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileInformation {
    /**
     * 文件分辨率
     */
    private String resolution;

    /**
     * 文件大小
     */
    private String size;
}
