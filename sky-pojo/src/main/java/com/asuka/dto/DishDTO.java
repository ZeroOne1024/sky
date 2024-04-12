package com.asuka.dto;

import com.asuka.entity.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDTO implements Serializable {

    private Long categoryId;
    private String description;
    private Long Id;
    private String image;
    private String name;
    private BigDecimal price;
    private Integer status;
    private ArrayList<DishFlavor> flavors = new ArrayList<>();

}
