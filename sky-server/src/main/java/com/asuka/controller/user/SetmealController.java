package com.asuka.controller.user;

import com.asuka.entity.Dish;
import com.asuka.entity.Setmeal;
import com.asuka.result.Result;
import com.asuka.service.SetmealService;
import com.asuka.vo.DishItemVO;
import com.asuka.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user/setmeal")
@RestController("UserSetmealController")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;


    @GetMapping("/list")
    @Cacheable(cacheNames = "setmealCache",key = "#categoryId")
    public Result<List<Setmeal>> selectSetmealById(Long categoryId){
        List<Setmeal> list = setmealService.selectSetmealByCategoryId(categoryId);

        return Result.success(list);
    }


    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> dishList(@PathVariable Long id){

        List<DishItemVO> list = setmealService.dishList(id);

        return Result.success(list);
    }


}
