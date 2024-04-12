package com.asuka.mapper;

import com.asuka.annotation.AutoFill;
import com.asuka.entity.Setmeal;
import com.asuka.enumeration.OperationType;
import com.asuka.vo.DishItemVO;
import com.asuka.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealMapper {


    List<Setmeal> selectSetmeal(Setmeal setmeal);

    List<DishItemVO> dishList(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);

    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    List<SetmealVO> selectSetmealWithCategory(Setmeal setmeal);

    SetmealVO selectSetmealById(Long id);

    void deleteByIds(List<Long> ids);
}
