package com.asuka.mapper;

import com.asuka.dto.EmployeeDTO;
import com.asuka.dto.EmployeeLoginDTO;
import com.asuka.dto.EmployeePageQueryDTO;
import com.asuka.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {


    @Select("select" +
            " id, name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user" +
            " from employee where username = #{name}")
    Employee getByUsername(String name);



    Integer insert(Employee employee);


    List<Employee> selectPage(EmployeePageQueryDTO query);
}
