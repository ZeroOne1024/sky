package com.asuka.controller.admin;

import com.asuka.dto.SetmealDTO;
import com.asuka.dto.SetmealPageDTO;
import com.asuka.entity.Setmeal;
import com.asuka.mapper.SetmealMapper;
import com.asuka.result.PageResult;
import com.asuka.result.Result;
import com.asuka.service.SetmealService;
import com.asuka.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/setmeal")
@RestController("adminSetmealController")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PutMapping
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.id")
    public Result updataSetmeal(@RequestBody SetmealDTO setmealDTO){
        setmealService.updateSetmealWithSetmealDish(setmealDTO);

        return Result.success();
    }


    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageDTO pageDTO){
        PageResult page = setmealService.page(pageDTO);
        return Result.success(page);
    }


    @PostMapping
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")
    public Result insert(@RequestBody SetmealDTO setmealDTO){
        setmealService.insert(setmealDTO);
        return Result.success();
    }



    @GetMapping("/{id}")
    public Result<SetmealVO> selectSetmealById(@PathVariable Long id){

        SetmealVO setmealVO = setmealService.selectSetmealById(id);

        return Result.success(setmealVO);
    }


    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result startOrStop(@PathVariable Integer status,Long id){
        setmealService.startOrStop(status,id);
        return Result.success();
    }



    @DeleteMapping
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result deleteByIds(@RequestParam List<Long> ids){
        setmealService.deleteByIds(ids);
        return Result.success();
    }



}
