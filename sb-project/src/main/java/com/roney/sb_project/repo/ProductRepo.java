package com.roney.sb_project.repo;

import com.roney.sb_project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    List<Product> findByProductNameContainingIgnoreCaseOrCategory_CategoryNameContainingIgnoreCase(String keyword, String keyword1);

    List<Product> findBySellerUserId(Integer userId);


    Product findByProductId(Integer productId);
}
