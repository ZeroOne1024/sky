package com.asuka.mapper;

import com.asuka.annotation.AutoFill;
import com.asuka.entity.Category;
import com.asuka.enumeration.OperationType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @AutoFill(value = OperationType.INSERT)
    void insertCategory(Category category);


    List<Category> select(Category category);

    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void updata(Category category);
}
