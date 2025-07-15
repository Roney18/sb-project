package com.roney.sb_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductDto {

    private int productID;
    private String productName;
    private Double price;
    private CategoryDto category;
//    private List<CartProductDto> cartProductDtoList;
}
