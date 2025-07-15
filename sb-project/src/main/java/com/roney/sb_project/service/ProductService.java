package com.roney.sb_project.service;

import com.roney.sb_project.dto.CategoryDto;
import com.roney.sb_project.dto.ProductDto;
import com.roney.sb_project.model.Product;
import com.roney.sb_project.repo.ProductRepo;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {


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
}
