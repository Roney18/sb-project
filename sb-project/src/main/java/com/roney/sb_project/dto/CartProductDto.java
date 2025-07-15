package com.roney.sb_project.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CartProductDto {

    private int cpId;
    private ProductDto product;
    private int quantity;
//    private CartDto Cart;

}
