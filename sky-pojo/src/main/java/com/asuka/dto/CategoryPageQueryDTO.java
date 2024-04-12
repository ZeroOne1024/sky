package com.asuka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPageQueryDTO implements Serializable {

    private String name;
    private Integer page;
    private Integer pageSize;
    private Integer type;

}
