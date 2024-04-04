package com.asuka.interceptor;

import com.asuka.constant.JwtClaimsConstant;
import com.asuka.context.BaseContext;
import com.asuka.properties.JwtProperties;
import com.asuka.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Asuka
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {


    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("当前拦截器线程id: {} ",Thread.currentThread().getId());

        if( !(handler instanceof HandlerMethod)){
            return true;
        }

        if(request.getRequestURL().toString().contains("admin/common/image/")){
            return true;
        }

        String token = request.getHeader(jwtProperties.getAdminTokenName());

        try {
            log.info("jwt校验:{}",token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(),token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());

            //将id存到线程里
            BaseContext.setCurrentId(empId);

            log.info("当前员工id:{}",empId);
            return true;
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }



}
