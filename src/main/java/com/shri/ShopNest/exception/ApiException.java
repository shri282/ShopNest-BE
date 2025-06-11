package com.shri.ShopNest.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiException extends RuntimeException{
    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    public ApiException(HttpStatus status, String errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
}
