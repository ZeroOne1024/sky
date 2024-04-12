package com.asuka.controller.admin;

import com.asuka.dto.DishDTO;
import com.asuka.dto.DishPageQueryDTO;
import com.asuka.entity.Dish;
import com.asuka.result.PageResult;
import com.asuka.result.Result;
import com.asuka.service.DishService;
import com.asuka.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
@ApiOperation("菜品接口")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redis;

    @PostMapping
    public Result insertDish(@RequestBody DishDTO dishDTO){
        log.info("插入菜品,{}",dishDTO);

        cleanCache("dish_"+dishDTO.getCategoryId());

        dishService.insertDish(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult> dishPageQuery(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询,{}",dishPageQueryDTO);
        PageResult pageResult = dishService.dishPageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }


    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids){
        log.info("删除菜品,{}",ids);
        dishService.delete(ids);

        cleanCache("dish_*");

        return Result.success();
    }


    @GetMapping("/{id}")
    public Result<DishVO> selectById(@PathVariable Long id){
        return Result.success(dishService.selectById(id));
    }


    @GetMapping("/list")
    public Result<List<Dish>> selectByCategoryId(Long categoryId){
        log.info("根据categoryId: {} 查询菜品",categoryId);
        List<Dish> list = dishService.selectByCategoryId(categoryId);

        return Result.success(list);
    }




    @PutMapping
    public Result updata(@RequestBody DishDTO dishDTO){
        log.info("更新菜品,{}",dishDTO);

        dishService.updata(dishDTO);

        cleanCache("dish_*");

        return Result.success();
    }


    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status,Long id){

        dishService.startOrStop(status,id);

        cleanCache("dish_*");

        return Result.success();
    }




    //清理redis
    private void cleanCache(String pattern){
        log.info("清理redis,{}",pattern);
        Set keys = redis.keys(pattern);
        log.info("清理的keys,{}",keys);
        redis.delete(keys);

    }


}
