package com.example.newcompare.common.scheduleTask.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResult {
    Integer errcode;
    String errmsg;
    CompanyData data;
    String err;
}
