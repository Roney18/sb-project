package com.roney.sb_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {

    private long productID;
    private String productName;
    private Double price;
    private String categoryName;

}
