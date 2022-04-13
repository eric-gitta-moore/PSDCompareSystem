package com.example.newcompare.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseResult implements Serializable
{
    private static final long srialVersionUID = 1L;

    private Integer errcode;
    private String errmsg;
    private Object data;
    private Object err;
}
