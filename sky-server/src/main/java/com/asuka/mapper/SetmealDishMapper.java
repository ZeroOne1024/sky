package com.asuka.mapper;

import com.asuka.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    List<Long> getSetmealIdsByDishIds(List<Long> ids);


    void deleteBySetmealId(Long setmealId);

    void insert(List<SetmealDish> list);

    List<SetmealDish> selectBySetmealId(Long id);

    void deleteBySetmealIds(List<Long> ids);
}
