package com.mrui22.ecommerce.common;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class APIResponse {
    private final boolean success;
    private final String message;

    public APIResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeStamp() {
        return LocalDateTime.now().toString();
    }
}
