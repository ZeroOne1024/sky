package com.asuka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetmealPageDTO implements Serializable {

    Long categoryId;
    String name;
    Integer page;
    Integer pageSize;
    Integer status;
}
