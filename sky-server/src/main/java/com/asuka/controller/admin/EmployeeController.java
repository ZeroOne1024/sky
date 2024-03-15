package com.asuka.controller.admin;

import com.asuka.constant.JwtClaimsConstant;
import com.asuka.dto.EmployeeDTO;
import com.asuka.dto.EmployeePageQueryDTO;
import com.asuka.entity.Employee;
import com.asuka.properties.JwtProperties;
import com.asuka.result.PageResult;
import com.asuka.service.EmployeeService;
import com.asuka.utils.JwtUtil;
import com.asuka.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.asuka.result.Result;
import com.asuka.dto.EmployeeLoginDTO;


import java.util.HashMap;
import java.util.Map;

@RequestMapping("/admin/employee")
@RestController
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtProperties jwtProperties;



    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoingDTO){
        log.info("员工登录:{}",employeeLoingDTO);

        Employee employee = employeeService.login(employeeLoingDTO);

        //jwt
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID,employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    @PostMapping("/logout")
    @ApiOperation("员工退出")
    public Result<String> logout(){
        return Result.success();
    }


    @PostMapping
    @ApiOperation(value = "插入")
    public Result insertEmployee(@RequestBody EmployeeDTO employeeDTO){
        log.info("当前请求接口线程id: {} ",Thread.currentThread().getId());

        log.info("插入员工,{}",employeeDTO);

        employeeService.insert(employeeDTO);

        return Result.success();
    }


    @GetMapping("/page")
    public Result<PageResult> select(EmployeePageQueryDTO query){
        log.info("分页查询请求: {}",query);

        PageResult pageResult = employeeService.selectPage(query);

        return Result.success(pageResult);
    }


    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用状态")
    public Result changeStatus(@PathVariable Integer status, Long id){
        log.info("id {} 状态修改为 {}",id,status);

        employeeService.changeStatus(status,id);
        return Result.success();
    }


    @GetMapping("/{id}")
    @ApiOperation("根据id查询")
    public Result<Employee> selectById(@PathVariable Long id){
        log.info("查询id为{}的用户",id);
        Employee e = employeeService.selectById(id);
        return Result.success(e);
    }


    @PutMapping
    @ApiOperation("修改员工数据")
    public Result updata(@RequestBody Employee employee){
        log.info("修改员工数据为 {}",employee);
        employeeService.updata(employee);
        return Result.success();
    }





}
