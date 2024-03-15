package com.asuka.service;

import com.asuka.dto.EmployeeDTO;
import com.asuka.dto.EmployeeLoginDTO;
import com.asuka.dto.EmployeePageQueryDTO;
import com.asuka.entity.Employee;
import com.asuka.result.PageResult;

import java.util.Map;

/**
 * @author Asuka
 */
public interface EmployeeService {


    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void insert(EmployeeDTO employeeDTO);


    PageResult selectPage(EmployeePageQueryDTO query);

    void changeStatus(Integer status, Long id);

    Employee selectById(Long id);

    void updata(Employee employee);
}
