package com.asuka.controller.user;


import com.asuka.result.Result;
import com.asuka.service.DishService;
import com.asuka.vo.DishVO;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user/dish")
@RestController("UserDishController")
@Slf4j
public class DishController {

    @Autowired
    private DishService dish;

    @Autowired
    private RedisTemplate redis;

    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId){
        log.info("微信查询分类id: ,{}",categoryId);
        String key="dish_"+categoryId;

        //查询redis里是否存在
        ValueOperations valueOperations = redis.opsForValue();
        List<DishVO>  list = (List<DishVO>) valueOperations.get(key);

        if(list != null && !list.isEmpty()){
            log.info("redis查询,{}",list);
            return Result.success(list);
        }

        //redis里不存在
        list = dish.selectDIshWithFlavor(categoryId);

        valueOperations.set(key,list);

        return Result.success(list);
    }



}
