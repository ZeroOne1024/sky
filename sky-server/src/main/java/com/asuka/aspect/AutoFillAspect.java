package com.asuka.aspect;

import com.asuka.annotation.AutoFill;
import com.asuka.constant.AutoFillConstant;
import com.asuka.context.BaseContext;
import com.asuka.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.asuka.mapper.*.*(..)) && @annotation(com.asuka.annotation.AutoFill)")
    public void autoFillPointCut(){

    }

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("开始公共字段填充...");

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);

        Object[] args = joinPoint.getArgs();

        if(args == null || args.length == 0){
            return;
        }


        LocalDateTime now = LocalDateTime.now();
        Long userId = BaseContext.getCurrentId();

        Object arg = args[0];

        if(annotation.value().equals(OperationType.INSERT)){
            try {
                arg.getClass().getMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class).invoke(arg,now);
                arg.getClass().getMethod(AutoFillConstant.SET_CREATE_USER,Long.class).invoke(arg,userId);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        try {
            arg.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class).invoke(arg,now);
            arg.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER,Long.class).invoke(arg,userId);
        }catch (Exception e){
            e.printStackTrace();
        }

        log.info("公共字段填充完毕...");
    }


}
