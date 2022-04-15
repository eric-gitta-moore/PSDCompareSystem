package com.example.newcompare.common.scheduleTask.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyData {
    private String skipCount;
    private Integer doneCount;
    private String urls[];
    private String skipInfo[];
    private String total;
}
