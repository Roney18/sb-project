package com.roney.sb_project.service;

import com.roney.sb_project.dto.CartDto;
import com.roney.sb_project.dto.CartProductDto;
import com.roney.sb_project.dto.CategoryDto;
import com.roney.sb_project.dto.ProductDto;
import com.roney.sb_project.model.*;
import com.roney.sb_project.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsumerService {


    @Autowired
    private CartProductRepo cartProductRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepo productRepo;

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

    public boolean saveCart(String username, Product product) {
        Cart cart = cartRepo.findByUserUsername(username);
        CartProduct cproduct = cart.getCartProducts().stream()
                .filter(cp -> cp.getProduct().getProductId().equals(product.getProductId()))
                .findFirst()
                .orElse(null);
        if(cproduct != null){
            return false;
        }
        Category category = categoryRepo.findByCategoryName(product.getCategory().getCategoryName());
        if(category == null){
            Category saved = categoryRepo.save(product.getCategory());
            product.setCategory(saved);
        }
        else{
            product.setCategory(category);
        }
        CartProduct cpnew = new CartProduct();
        cpnew.setCart(cart);
        cpnew.setQuantity(1);
        cpnew.setProduct(product);
        cart.getCartProducts().add(cpnew);
        cartProductRepo.save(cpnew);
        cartRepo.save(cart);
        return true;
    }

    public boolean modifyCartQuantity(String username, CartProduct cartProduct) {
        User user = userRepo.findByUsername(username);
        Cart cart = cartRepo.findByUserUsername(username);

        if(cart == null){
            return false;
        }
        if(cartProduct.getQuantity()==0){
            try{
                cartProductRepo.deleteByCartByUserUserIdAndProductProductId(user.getUserId(),cartProduct.getProduct().getProductId());
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        CartProduct cproduct = cart.getCartProducts().stream()
                .filter(cp -> cp.getProduct().getProductId().equals(cartProduct.getProduct().getProductId()))
                .findFirst()
                .orElse(null);
        if(cproduct==null) {
            Category category = categoryRepo.findByCategoryName(cartProduct.getProduct().getCategory().getCategoryName());
            if(category == null){
                Category saved = categoryRepo.save(cartProduct.getProduct().getCategory());
                cartProduct.getProduct().setCategory(saved);
            }
            else{
                cartProduct.getProduct().setCategory(category);
            }
            cartProduct.setCart(cart);
            productRepo.save(cartProduct.getProduct());
            cartProductRepo.save(cartProduct);
            return true;
        }
       cproduct.setQuantity(cartProduct.getQuantity());
        productRepo.save(cproduct.getProduct());
        cartProductRepo.save(cproduct);
        return true;


    }
}