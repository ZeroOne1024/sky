package com.asuka.service.impl;

import com.asuka.constant.MessageConstant;
import com.asuka.constant.PasswordConstant;
import com.asuka.constant.StatusConstant;
import com.asuka.context.BaseContext;
import com.asuka.dto.EmployeeDTO;
import com.asuka.dto.EmployeeLoginDTO;
import com.asuka.dto.EmployeePageQueryDTO;
import com.asuka.entity.Employee;
import com.asuka.exception.AccountNotFoundException;
import com.asuka.mapper.EmployeeMapper;
import com.asuka.result.PageResult;
import com.asuka.service.EmployeeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Asuka
 */

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        Employee employee = employeeMapper.getByUsername(username);

        if(employee == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if(!password.equals(employee.getPassword())){
            throw new AccountNotFoundException(MessageConstant.PASSWORD_ERROR);
        }


        if(StatusConstant.DISABLE.equals(employee.getStatus())){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_LOCKED);
        }

        return employee;
    }



    @Override
    public void insert(EmployeeDTO employeeDTO) {

        log.info("当前服务接口线程id: {} ",Thread.currentThread().getId());

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        //设置状态码 1 正常 0 锁
        employee.setStatus(StatusConstant.ENABLE);

        //设置密码
        String password = DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes());
        employee.setPassword(password);


        employeeMapper.insert(employee);
    }

    @Override
    public PageResult selectPage(EmployeePageQueryDTO query) {




        PageHelper.startPage(query.getPage(), query.getPageSize());
        Page<Employee> p = (Page<Employee>) employeeMapper.selectPage(query);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(p.getTotal());
        pageResult.setRecords(p.getResult());

        return pageResult;
    }

    @Override
    public void changeStatus(Integer status, Long id) {

        Employee employee = Employee.builder()
                                .id(id)
                                .status(status)
                                .build();


        employeeMapper.update(employee);
    }


    @Override
    public Employee selectById(Long id) {

        return employeeMapper.selectById(id);
    }


    @Override
    public void updata(Employee employee) {

        employeeMapper.update(employee);
    }
}
