package com.asuka.controller.user;

import com.asuka.constant.JwtClaimsConstant;
import com.asuka.dto.UserLoginDTO;
import com.asuka.properties.JwtProperties;
import com.asuka.result.Result;
import com.asuka.service.UserService;
import com.asuka.utils.JwtUtil;
import com.asuka.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.asuka.entity.User;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;


    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("微信用户登录,{}",userLoginDTO);

        //用户登录
        User user = userService.login(userLoginDTO);


        //生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());

        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token).build();

        return Result.success(userLoginVO);
    }



}
