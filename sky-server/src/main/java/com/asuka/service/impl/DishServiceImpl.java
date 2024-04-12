package com.asuka.service.impl;

import com.asuka.constant.MessageConstant;
import com.asuka.constant.StatusConstant;
import com.asuka.dto.DishDTO;
import com.asuka.dto.DishPageQueryDTO;
import com.asuka.entity.Dish;
import com.asuka.entity.DishFlavor;
import com.asuka.exception.DeletionNotAllowedException;
import com.asuka.mapper.DishFlavorMapper;
import com.asuka.mapper.DishMapper;
import com.asuka.mapper.SetmealDishMapper;
import com.asuka.result.PageResult;
import com.asuka.service.DishService;
import com.asuka.vo.DishVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;


    @Autowired
    private SetmealDishMapper setmealDishMapper;



    @Override
    @Transactional
    public void insertDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        log.info("插入dish,{}",dish);
        dishMapper.insertDish(dish);

        ArrayList<DishFlavor> flavors = dishDTO.getFlavors();

        if(flavors != null && flavors.size() != 0){
            log.info("插入dishFlavor,{}",flavors);
            flavors.forEach(item->{item.setDishId(dish.getId());});

            dishFlavorMapper.insertDishFlavor(flavors);
        }


    }

    @Override
    public PageResult dishPageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());


        Dish dish = new Dish();
        BeanUtils.copyProperties(dishPageQueryDTO,dish);

        //dish.setStatus(1);

        Page<DishVO> p = (Page<DishVO>)dishMapper.selectDishCateg(dish);

        PageResult pr = new PageResult();
        pr.setTotal(p.getTotal());
        pr.setRecords(p.getResult());


        return pr;
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        if(ids == null || ids.isEmpty()){
            throw new RuntimeException("删除内容为空");
        }

        List<Dish> dishes = dishMapper.getById(ids);
        for (Dish dish : dishes) {
            if(dish.getStatus().equals(StatusConstant.ENABLE)){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }


        List<Long> list = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if(list != null && !list.isEmpty()){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        //删除dish表
        dishMapper.delete(ids);


        //删除口味表
        dishFlavorMapper.deleteByDishIds(ids);

    }



    @Override
    public DishVO selectById(Long id) {
        DishVO dishVO = dishMapper.selectDishCategoryById(id);

        DishFlavor df = DishFlavor.builder().dishId(id).build();
        dishVO.setFlavors(dishFlavorMapper.select(df));

        return dishVO;
    }


    @Override
    @Transactional
    public void updata(DishDTO dishDTO) {

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);


        ArrayList<DishFlavor> flavors = dishDTO.getFlavors();

        flavors.forEach(f->{
            f.setDishId(dishDTO.getId());
        });


        dishFlavorMapper.deleteByDishIds(List.of(dishDTO.getId()));

        if(!flavors.isEmpty()){
            dishFlavorMapper.insertDishFlavor(flavors);
        }


    }


    /**
     *
     * 查询dish表和dish_flavor表
     * */
    @Override
    public List<DishVO> selectDIshWithFlavor(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE).build();

        List<DishVO> dishList = dishMapper.selectList(dish);



        for (DishVO d : dishList) {

            List<DishFlavor> flavors = dishFlavorMapper.selectByDishId(d.getId());
            d.setFlavors(flavors);

        }


        return dishList;
    }


    @Override
    public void startOrStop(Integer status, Long id) {
        Dish dish = Dish.builder().status(status).id(id).build();

        dishMapper.update(dish);
    }


    @Override
    public List<Dish> selectByCategoryId(Long categoryId) {

        return dishMapper.selectByCategoryId(categoryId);
    }
}
