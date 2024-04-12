package com.asuka.service.impl;

import com.asuka.context.BaseContext;
import com.asuka.dto.CategoryDTO;
import com.asuka.dto.CategoryPageQueryDTO;
import com.asuka.dto.CategoryUpdataDto;
import com.asuka.entity.Category;
import com.asuka.mapper.CategoryMapper;
import com.asuka.result.PageResult;
import com.asuka.service.CategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public void insertCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                        .status(0)
                        .build();

        BeanUtils.copyProperties(categoryDTO,category);

        categoryMapper.insertCategory(category);

    }


    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        Category category = Category.builder()
                        .name(categoryPageQueryDTO.getName())
                        .type(categoryPageQueryDTO.getType())
                        .build();


        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());


        Page<Category> page = (Page<Category>) categoryMapper.select(category);

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }



    @Override
    public void updata(CategoryUpdataDto categoryUpdataDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryUpdataDto,category);

        categoryMapper.updata(category);
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Category category = Category.builder().id(id).status(status)
                        .build();

        categoryMapper.updata(category);
    }


    @Override
    public List<Category> list(Integer type) {
        Category category = Category.builder().type(type).build();
        categoryMapper.select(category);

        return categoryMapper.select(category);
    }
}
