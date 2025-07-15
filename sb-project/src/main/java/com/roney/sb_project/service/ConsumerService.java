package com.roney.sb_project.service;

import com.roney.sb_project.dto.CartDto;
import com.roney.sb_project.dto.CartProductDto;
import com.roney.sb_project.dto.CategoryDto;
import com.roney.sb_project.dto.ProductDto;
import com.roney.sb_project.model.Cart;
import com.roney.sb_project.repo.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsumerService {


    @Autowired
    private CartRepo cartRepo;
    public List<Cart> getCart(int userid) {
        List<Cart> find = cartRepo.findByUserUserId(userid);
        return find;
    }

    public CartDto getCarts(String username) {
        Cart cart = cartRepo.findByUserUsername(username);
        if(cart == null){
            return null;
        }
        return convertToCartDto(cart);
    }

    private CartDto convertToCartDto(Cart cart){
        List<CartProductDto> cartProductDTOs = cart.getCartProducts().stream()
                .map(cp -> new CartProductDto(
                        cp.getCpId(),
                        new ProductDto(
                                cp.getProduct().getProductId(),
                                cp.getProduct().getProductName(),
                                cp.getProduct().getPrice(),
                                new CategoryDto(cp.getProduct().getCategory().getCategoryName())
                        ),
                        cp.getQuantity()
                ))
                .collect(Collectors.toList());

        return new CartDto(cart.getCartId(), cart.getTotalAmount(), cartProductDTOs);
    }
}