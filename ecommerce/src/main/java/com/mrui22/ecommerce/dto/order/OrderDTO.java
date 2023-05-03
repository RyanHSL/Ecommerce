package com.mrui22.ecommerce.dto.order;

import com.mrui22.ecommerce.model.Order;

import javax.validation.constraints.NotNull;

public class OrderDTO {

    private Integer id;
    private @NotNull Integer userId;

    public OrderDTO() {
    }

    public OrderDTO(Order order) {
        this.setId(order.getId());
        //this.setUserId(order.getUserId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
