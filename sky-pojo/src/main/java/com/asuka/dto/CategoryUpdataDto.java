package com.asuka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdataDto implements Serializable {

    private Long id;
    private String name;
    private Integer sort;
    private Integer type;

}
