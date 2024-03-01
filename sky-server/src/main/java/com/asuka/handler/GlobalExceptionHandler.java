package com.asuka.handler;

import com.asuka.constant.MessageConstant;
import com.asuka.exception.BaseException;
import com.asuka.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author Asuka
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.info("异常信息:{}",ex.getMessage());
        return Result.error(ex.getMessage());
    }


    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //Duplicate entry '123456' for key 'employee.idx_username'

        if(ex.getMessage().contains("Duplicate entry")){
            String msg = ex.getMessage().split(" ")[2]+ " " + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        }

        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }


}
