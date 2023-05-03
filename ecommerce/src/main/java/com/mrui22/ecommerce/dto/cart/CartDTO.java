package com.mrui22.ecommerce.dto.cart;

import io.swagger.models.auth.In;
import javax.validation.constraints.NotNull;

import java.util.List;

public class CartDTO {

    private List<CartItemDTO> cartItems;
    private double totalCost;

    public CartDTO(List<CartItemDTO> cartItemDtoList, double totalCost) {
        this.cartItems = cartItemDtoList;
        this.totalCost = totalCost;
    }

    public List<CartItemDTO> getcartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItemDtoList) {
        this.cartItems = cartItemDtoList;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
