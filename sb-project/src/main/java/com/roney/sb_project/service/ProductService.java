package com.roney.sb_project.service;

import com.roney.sb_project.dto.CategoryDto;
import com.roney.sb_project.dto.ProductDto;
import com.roney.sb_project.model.Product;
import com.roney.sb_project.model.User;
import com.roney.sb_project.repo.ProductRepo;
import com.roney.sb_project.repo.UserRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepo productRepo;

    public List<ProductDto> search(String keyword) {
        List<Product> results = productRepo.findByProductNameContainingIgnoreCaseOrCategory_CategoryNameContainingIgnoreCase(keyword,keyword);
        return results.stream()
                .map(p -> new ProductDto(p.getProductId(),
                        p.getProductName(),
                        p.getPrice(),
                        new CategoryDto(p.getCategory().getCategoryName())))
                .collect(Collectors.toList());
    }


    public ResponseEntity<?> getAllProducts(String username) {
        try{
            User user = userRepo.findByUsername(username);
            List<Product> productList = productRepo.findBySellerUserId(user.getUserId());
            return ResponseEntity.ok(productList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<?> getProductById(String username, int productId) {

        User user = userRepo.findByUsername(username);

        Product product = productRepo.findByProductId(productId);

        if(product == null){
            return ResponseEntity.notFound().build();
        }
        if(product.getSeller().getUserId() != user.getUserId()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
}
