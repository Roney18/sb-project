package com.roney.sb_project.controllers;


import com.roney.sb_project.repo.ProductRepo;
import com.roney.sb_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/seller")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<?> getAllProducts(@AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        return productService.getAllProducts(username);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int productId){
        String username = userDetails.getUsername();
        return productService.getProductById(username,productId);
    }
}
