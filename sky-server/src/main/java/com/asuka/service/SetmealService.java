package com.asuka.service;

import com.asuka.dto.SetmealDTO;
import com.asuka.dto.SetmealPageDTO;
import com.asuka.entity.Dish;
import com.asuka.entity.Setmeal;
import com.asuka.result.PageResult;
import com.asuka.vo.DishItemVO;
import com.asuka.vo.DishVO;
import com.asuka.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    List<Setmeal> selectSetmealByCategoryId(Long categoryId);

    List<DishItemVO> dishList(Long id);

    void updateSetmealWithSetmealDish(SetmealDTO setmealDTO);

    PageResult page(SetmealPageDTO pageDTO);

    void insert(SetmealDTO setmealDTO);

    SetmealVO selectSetmealById(Long id);

    void startOrStop(Integer status,Long id);

    void deleteByIds(List<Long> ids);
}
