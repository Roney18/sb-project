package com.roney.sb_project.controllers;

import com.roney.sb_project.dto.CartDto;
import com.roney.sb_project.model.Cart;
import com.roney.sb_project.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
