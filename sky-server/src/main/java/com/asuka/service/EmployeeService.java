package com.asuka.service;

import com.asuka.dto.EmployeeDTO;
import com.asuka.dto.EmployeeLoginDTO;
import com.asuka.entity.Employee;

/**
 * @author Asuka
 */
public interface EmployeeService {


    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void insert(EmployeeDTO employeeDTO);


}
