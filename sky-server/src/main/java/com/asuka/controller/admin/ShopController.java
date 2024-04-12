package com.asuka.controller.admin;

import com.asuka.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@Slf4j
@ApiOperation("营业状态")
@RequestMapping("/admin/shop")
public class ShopController {

    private static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/status")
    public Result<Integer> selectStatus(){

        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("查询营业状态为,{}",status == 0 ? "打样" : "营业");

        return Result.success(status);
    }


    @PutMapping("/{status}")
    public Result updataStatus(@PathVariable Integer status){
        log.info("设置营业状态为,{}",status == 0 ? "打样" : "营业");
        redisTemplate.opsForValue().set(KEY,status);
        return Result.success();
    }



}
