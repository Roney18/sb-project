package com.roney.sb_project.controllers;


import com.roney.sb_project.model.Product;
import com.roney.sb_project.model.User;
import com.roney.sb_project.repo.ProductRepo;
import com.roney.sb_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/product")
    public ResponseEntity<?> postProduct(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Product product){
        String username = userDetails.getUsername();
        return productService.saveProduct(username,product);
    }

    @PutMapping("/product")
    public ResponseEntity<?> modifyProduct(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Product product){
        String username = userDetails.getUsername();
        return productService.modifyProduct(username,product);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int productId){
        String username = userDetails.getUsername();
        return productService.deleteProduct(username,productId);
    }
}
