package com.asuka.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishItemVO {

    private Integer copies;
    private String description;
    private String image;
    private String name;

}
