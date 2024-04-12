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

@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwt;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("当前拦截器线程id: {} ",Thread.currentThread().getId());

        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        String token = request.getHeader(jwt.getUserTokenName());


        try {
            log.info("jwt校验:{}",token);
            Claims claims = JwtUtil.parseJWT(jwt.getUserSecretKey(),token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());

            //将id存到线程里
            BaseContext.setCurrentId(userId);

            log.info("当前微信用户id:{}",userId);
            return true;
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }

    }

}
