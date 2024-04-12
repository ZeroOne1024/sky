package com.asuka.service;

import com.asuka.dto.DishDTO;
import com.asuka.dto.DishPageQueryDTO;
import com.asuka.entity.Dish;
import com.asuka.result.PageResult;
import com.asuka.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DishService {
    void insertDish(DishDTO dishDTO);

    PageResult dishPageQuery(DishPageQueryDTO dishPageQueryDTO);

    DishVO selectById(Long id);

    void delete(List<Long> ids);

    void updata(DishDTO dishDTO);

    List<DishVO> selectDIshWithFlavor(Long categoryId);

    void startOrStop(Integer status, Long id);

    List<Dish> selectByCategoryId(Long categoryId);
}
