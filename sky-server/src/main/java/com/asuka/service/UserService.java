package com.asuka.service;

import com.asuka.dto.UserLoginDTO;
import com.asuka.entity.User;
import org.mapstruct.Mapper;

import javax.security.auth.login.LoginException;


public interface UserService {


    User login(UserLoginDTO userLoginDTO);
}
