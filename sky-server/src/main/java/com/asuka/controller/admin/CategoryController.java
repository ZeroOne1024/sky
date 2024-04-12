package com.asuka.controller.admin;

import com.asuka.dto.CategoryDTO;
import com.asuka.dto.CategoryPageQueryDTO;
import com.asuka.dto.CategoryUpdataDto;
import com.asuka.entity.Category;
import com.asuka.result.PageResult;
import com.asuka.result.Result;
import com.asuka.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/category")
@RestController
@Api("新建分类")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping
    public Result insertCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("创建分类: {}",categoryDTO);



        categoryService.insertCategory(categoryDTO);

        return Result.success();
    }


    @GetMapping("/page")
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分类分页查询: {}",categoryPageQueryDTO);

        return Result.success(categoryService.pageQuery(categoryPageQueryDTO));
    }


    @DeleteMapping
    public Result deleteById(Long id){
        log.info("根据id删除: {}",id);

        categoryService.deleteById(id);

        return Result.success();
    }

    @PutMapping
    public Result updata(@RequestBody CategoryUpdataDto categoryUpdataDto){
        categoryService.updata(categoryUpdataDto);

        return Result.success();
    }


    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status,Long id){
        log.info("启用或禁用,status: {},id: {}",status,id);

        categoryService.startOrStop(status,id);

        return Result.success();
    }


    @GetMapping("/list")
    public Result list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }


}
