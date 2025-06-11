package com.shri.ShopNest.exception.exceptions;

public class InvalidUserNameOrPasswordException extends RuntimeException{
    public InvalidUserNameOrPasswordException(String message) {
        super(message);
    }

}
