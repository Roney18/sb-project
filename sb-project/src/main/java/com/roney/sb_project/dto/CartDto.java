package com.roney.sb_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartDto {

    private int cartId;
    private Double totalAmount;
    private List<CartProductDto> cartProducts;
}
