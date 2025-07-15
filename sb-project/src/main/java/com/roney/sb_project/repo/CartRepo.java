package com.roney.sb_project.repo;

import com.roney.sb_project.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart,Integer> {

//    List<Cart> findByUserId(int userid);

    List<Cart> findByUserUserId(int userid);

    Cart findByUserUsername(String username);
}
