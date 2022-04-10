package com.example.newcompare.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("结果集对象")
public class Result {
    //状态码
   @ApiModelProperty("响应状态码")
    private int code;
    //消息
    @ApiModelProperty("响应消息")
    private String message;
    //返回获取的数据
    @ApiModelProperty("响应回的数据")
    private Object data;




    protected Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    /**
     *
     * @param code 状态码
     * @param message 返回的消息
     * @param data 获取的数据
     * @return 返回成功结果
     */
    public static Result success(int code, String message, Object data){
        return new Result(code,message,data);
    }


    /**
     *
     * @param data 获取的数据
     * @return 返回成功结果
     */
    public static Result success(Object data){
        return success(200,"success",data);
    }

    /**
     *
     * @param message 返回的消息
     * @return 返回成功结果
     */
    public static Result success(String message){
        return success(200,message,null);
    }

    /**
     *
     * @return 返回成功结果
     */
    public static Result success(){
        return success(200,"success",null);
    }

    /**
     *
     * @param message 返回的消息
     * @param data 获取的数据
     * @return 返回成功的结果
     */
    public static Result success(String message, Object data){
        return success(200,message,data);
    }

    /**
     *
     * @param message 返回的消息
     * @return 返回失败的结果
     */
    public static Result fail(String message){
        return fail(400,message,null);
    }

    /**
     *
     * @param message 返回的消息
     * @param data 获取的数据
     * @return 返回失败的结果
     */
    public static Result fail(String message,Object data){
        return fail(400,message,data);
    }

    /**
     *
     * @param code 状态码
     * @param message 返回的消息
     * @param data 获取的数据
     * @return 返回失败的结果
     */
    public static Result fail(int code,String message,Object data){
        return new Result(code,message,data);
    }
}
