package com.roney.sb_project.service;

import com.roney.sb_project.dto.CategoryDto;
import com.roney.sb_project.dto.ProductDto;
import com.roney.sb_project.model.Category;
import com.roney.sb_project.model.Product;
import com.roney.sb_project.model.User;
import com.roney.sb_project.repo.CategoryRepo;
import com.roney.sb_project.repo.ProductRepo;
import com.roney.sb_project.repo.UserRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

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

    public boolean userHasRole(User user, String roleName) {
        return user.getRoles()
                .stream()
                .anyMatch(role -> role.getRole().equalsIgnoreCase(roleName));
    }

    public ResponseEntity<?> saveProduct(String username, Product product) {

        User user = userRepo.findByUsername(username);
        if (user == null || !userHasRole(user, "SELLER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User is not authorized to create products.");
        }
        Category category = categoryRepo.findByCategoryName(product.getCategory().getCategoryName());
        if(category == null){
            Category sCategory = categoryRepo.save(product.getCategory());
            product.setCategory(sCategory);
        }
        else {
            product.setCategory(category);
        }
        product.setSeller(user);
        Product saved = productRepo.save(product);
        try{
            return ResponseEntity.created(new URI("http://localhost/api/auth/seller/product/" + saved.getProductId()))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<?> modifyProduct(String username, Product product) {
        User user = userRepo.findByUsername(username);
      Product pro = productRepo.findByProductId(product.getProductId());
       if(pro == null){
           return ResponseEntity.notFound().build();
       }
        pro.setPrice(product.getPrice());
        pro.setSeller(user);
        pro.setProductName(product.getProductName());
        Category cat = categoryRepo.findByCategoryName(product.getCategory().getCategoryName());
        if(cat == null){
            Category saved = categoryRepo.save(product.getCategory());
            pro.setCategory(saved);
        }
        else {
            pro.setCategory(cat);
        }
        productRepo.save(pro);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteProduct(String username, int productId) {
        User user = userRepo.findByUsername(username);
        Product pro = productRepo.findByProductId(productId);
        if(pro == null){
            return ResponseEntity.notFound().build();
        }
        if(pro.getSeller().getUserId().equals(user.getUserId())){
            categoryRepo.deleteByProduct_ProductId(productId);
            productRepo.delete(pro);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
