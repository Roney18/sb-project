package com.roney.sb_project.repo;

import com.roney.sb_project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {


    Category findByCategoryName(String categoryName);

    void deleteByProduct_ProductId(int productId);
}
