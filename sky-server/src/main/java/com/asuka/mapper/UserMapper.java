package com.asuka.mapper;

import com.asuka.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;


@Mapper
public interface UserMapper {



    Long insertUser(User user);

    @Select("select " +
            "id, openid, name, phone, sex, id_number, avatar, create_time " +
            "from " +
            "user " +
            "where " +
            "openid = #{openId}")
    User selectBuOpenId(String openId);


    @Select("select" +
            " id, openid, name, phone, sex, id_number, avatar, create_time" +
            " from" +
            " user" +
            " where" +
            " id = #{id}")
    User selectById(Long id);


    @Select("select count(id) from user" +
            " where DATE (create_time) = #{now}")
    Integer newUsersNumber(LocalDate now);


}
