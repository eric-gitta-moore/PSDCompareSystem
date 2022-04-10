package com.example.newcompare.exception;

import com.example.newcompare.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class GlobalHandler {
    /**
     * 二维码获取出错处理
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = IOException.class)
    public Result handler(IOException e){
        e.printStackTrace();
        return Result.fail("IO出错");
    }

}
