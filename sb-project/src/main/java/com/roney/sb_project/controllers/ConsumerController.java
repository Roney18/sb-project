package com.roney.sb_project.controllers;

import com.roney.sb_project.dto.CartDto;
import com.roney.sb_project.model.Cart;
import com.roney.sb_project.model.CartProduct;
import com.roney.sb_project.model.Product;
import com.roney.sb_project.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/consumer/cart")
    public ResponseEntity<?> returnCart(@AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        CartDto results = consumerService.getCarts(username);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/consumer/cart")
    public ResponseEntity<?> saveCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Product product){
        String username = userDetails.getUsername();
        boolean result = consumerService.saveCart(username,product);
        if(result){
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(409).build();
        }
    }

    @PutMapping("/consumer/cart")
    public ResponseEntity<?> modifyCartQuantity(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CartProduct cartProduct){
        String username = userDetails.getUsername();
        boolean result= consumerService.modifyCartQuantity(username,cartProduct);
        if(result){
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(409).build();
        }
    }
}
