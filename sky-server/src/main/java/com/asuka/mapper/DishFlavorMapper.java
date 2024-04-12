package com.asuka.mapper;

import com.asuka.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface DishFlavorMapper {


    void insertDishFlavor(ArrayList<DishFlavor> flavors);

    void deleteByDishIds(List<Long> ids);

    List<DishFlavor> select(DishFlavor dishFlavor);


    @Select("select " +
            "id, dish_id, name, value " +
            "from " +
            "dish_flavor " +
            "where dish_id = #{id}")
    List<DishFlavor> selectByDishId(Long id);

}
