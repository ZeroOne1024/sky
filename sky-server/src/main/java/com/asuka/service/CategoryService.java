package com.asuka.service;


import com.asuka.dto.CategoryDTO;
import com.asuka.dto.CategoryPageQueryDTO;
import com.asuka.dto.CategoryUpdataDto;
import com.asuka.entity.Category;
import com.asuka.result.PageResult;

import java.util.List;

public interface CategoryService {


    void insertCategory(CategoryDTO categoryDTO);

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void deleteById(Long id);

    void updata(CategoryUpdataDto categoryUpdataDto);

    void startOrStop(Integer status, Long id);

    List<Category> list(Integer type);
}
