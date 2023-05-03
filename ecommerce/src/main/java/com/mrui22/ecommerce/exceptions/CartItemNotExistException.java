package com.mrui22.ecommerce.exceptions;

public class CartItemNotExistException extends IllegalArgumentException{

    public CartItemNotExistException(String msg) {
        super(msg);
    }
}
