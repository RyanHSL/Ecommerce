package com.mrui22.ecommerce.repository;

import com.mrui22.ecommerce.model.Order;
import com.mrui22.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByUserOrderByCreatedDateDesc(User user);

}
