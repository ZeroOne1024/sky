package com.asuka.service.impl;

import com.asuka.constant.StatusConstant;
import com.asuka.dto.SetmealDTO;
import com.asuka.dto.SetmealPageDTO;
import com.asuka.entity.Dish;
import com.asuka.entity.Setmeal;
import com.asuka.entity.SetmealDish;
import com.asuka.mapper.DishMapper;
import com.asuka.mapper.SetmealDishMapper;
import com.asuka.mapper.SetmealMapper;
import com.asuka.result.PageResult;
import com.asuka.service.SetmealService;
import com.asuka.vo.DishItemVO;
import com.asuka.vo.DishVO;
import com.asuka.vo.SetmealVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {


    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;



    @Override
    @Transient
    public void updateSetmealWithSetmealDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        //更新套餐表
        setmealMapper.update(setmeal);


        //删除套餐菜品关联表
        Long setmealId = setmealDTO.getId();
        setmealDishMapper.deleteBySetmealId(setmealId);

        //套餐菜品关联表设置 setmeal id
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(item->{
            item.setSetmealId(setmealId);
        });

        //插入
        setmealDishMapper.insert(setmealDishes);
    }



    @Override
    public List<Setmeal> selectSetmealByCategoryId(Long categoryId) {


        Setmeal setmeal = Setmeal
                .builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();

        List<Setmeal> list = setmealMapper.selectSetmeal(setmeal);


        return list;
    }


    @Override
    public List<DishItemVO> dishList(Long id) {
        List<DishItemVO> list = setmealMapper.dishList(id);
        return list;
    }


    @Override
    public PageResult page(SetmealPageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPage(),pageDTO.getPageSize());

        Setmeal setmeal = Setmeal.builder()
                .name(pageDTO.getName())
                .categoryId(pageDTO.getCategoryId())
                .status(pageDTO.getStatus())
                .build();

        Page<SetmealVO> page = (Page<SetmealVO>) setmealMapper.selectSetmealWithCategory(setmeal);

        return new PageResult(page.getTotal(),page.getResult());
    }


    @Override
    //插入套餐
    @Transient
    public void insert(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();

        BeanUtils.copyProperties(setmealDTO,setmeal);

        //插入套餐表
        setmealMapper.insert(setmeal);

        //插入口味
        List<SetmealDish> list = setmealDTO.getSetmealDishes();

        list.forEach(item->{
            item.setSetmealId(setmealDTO.getId());
        });

        setmealDishMapper.insert(list);
    }


    @Override
    public SetmealVO selectSetmealById(Long id) {
        //查询setmeal和category的name
        SetmealVO setmealVO = setmealMapper.selectSetmealById(id);

        //通过id查询setmealDish
        setmealVO.setSetmealDishes(setmealDishMapper.selectBySetmealId(id));

        return setmealVO;
    }


    @Override
    public void startOrStop(Integer status,Long id) {
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();

        setmealMapper.update(setmeal);
    }


    @Override
    @Transient
    public void deleteByIds(List<Long> ids) {
        setmealMapper.deleteByIds(ids);
        setmealDishMapper.deleteBySetmealIds(ids);
    }
}
