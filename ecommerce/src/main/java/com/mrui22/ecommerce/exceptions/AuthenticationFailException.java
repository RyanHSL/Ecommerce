package com.mrui22.ecommerce.exceptions;

public class AuthenticationFailException extends IllegalArgumentException {

    public AuthenticationFailException(String msg) {
        super(msg);
    }
}
