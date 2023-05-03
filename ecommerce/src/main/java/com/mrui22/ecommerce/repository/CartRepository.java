package com.mrui22.ecommerce.repository;

import com.mrui22.ecommerce.model.Cart;
import com.mrui22.ecommerce.model.User;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserOrderByCreateDateDesc(User user);

    List<Cart> deleteByUser(User user);
}
