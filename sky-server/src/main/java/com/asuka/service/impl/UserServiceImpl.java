package com.asuka.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asuka.constant.MessageConstant;
import com.asuka.constant.WeChatLoginConstant;
import com.asuka.dto.UserLoginDTO;
import com.asuka.entity.User;
import com.asuka.exception.LoginFailedException;
import com.asuka.mapper.UserMapper;
import com.asuka.properties.WeChatProperties;
import com.asuka.service.UserService;
import com.asuka.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.security.auth.login.LoginException;
import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final static String WX_LOGIN="https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties wcp;

    @Autowired
    private UserMapper userMapper;
    

    @Override
    @Transient
    public User login(UserLoginDTO userLoginDTO){

        String openid = getOpenid(userLoginDTO.getCode());

        if(openid == null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }


        //查询是否存在
        User u = userMapper.selectBuOpenId(openid);


        if(u != null){
            return u;
        }


        //不存在 新增
        User user = User.builder()
                .openid(openid)
                .createTime(LocalDateTime.now())
                .build();

        Long id = userMapper.insertUser(user);


        return User.builder().id(id).openid(openid).build();
    }


    private String getOpenid(String code){
        Map<String,String> map = new HashMap<>();

        map.put(WeChatLoginConstant.APP_ID,wcp.getAppid());
        map.put(WeChatLoginConstant.SECRET,wcp.getSecret());
        map.put(WeChatLoginConstant.JS_CODE,code);
        map.put(WeChatLoginConstant.GRANT_TYPE,WeChatLoginConstant.AUTHORIZATION_CODE);

        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        log.info("微信登录返回的json,{}",json);

        //解析json
        JSONObject jsonObject = JSON.parseObject(json);

        String openId = jsonObject.getString(WeChatLoginConstant.OPEN_ID);
        String sessionKey = jsonObject.getString(WeChatLoginConstant.SEESSION_KEY);

        return openId;
    }




}
