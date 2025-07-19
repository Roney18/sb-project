package com.roney.sb_project.repo;

import com.roney.sb_project.model.CartProduct;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepo extends JpaRepository<CartProduct,Integer> {

    @Transactional
    @Query(value = "DELETE FROM cart_product WHERE product_id = :productId AND cart_id = (SELECT cart_id FROM cart WHERE user_user_id = :userId)", nativeQuery = true)
    void deleteByCartByUserUserIdAndProductProductId(Integer userId, Integer productId);
}
