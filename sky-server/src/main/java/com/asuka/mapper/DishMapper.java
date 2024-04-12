package com.asuka.mapper;

import com.asuka.annotation.AutoFill;
import com.asuka.entity.Dish;
import com.asuka.enumeration.OperationType;
import com.asuka.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    @AutoFill(OperationType.INSERT)
    void insertDish(Dish dish);



    @Select("select count(id) from dish where  category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    //Page<Dish> select(Dish dish);
    List<DishVO> selectDishCateg(Dish dish);


    DishVO selectDishCategoryById(Long id);

    void delete(List<Long> ids);

    List<Dish> getById(List<Long> ids);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);


    List<DishVO> selectList(Dish dish);

    @Select("select " +
            "id, name, category_id, price, image, description, status, create_time, update_time, create_user, update_user " +
            "from " +
            "dish " +
            "where " +
            "category_id = #{categoryId}")
    List<Dish> selectByCategoryId(Long categoryId);
}
