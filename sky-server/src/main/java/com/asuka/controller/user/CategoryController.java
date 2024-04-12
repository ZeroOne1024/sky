package com.asuka.controller.user;

import com.asuka.entity.Category;
import com.asuka.result.Result;
import com.asuka.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user/category")
@RestController("UserCategoryController")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService category;

    @GetMapping("/list")
    public Result<List<Category>> list(Integer type){
        List<Category> list = category.list(type);
        return Result.success(list);
    }



}
