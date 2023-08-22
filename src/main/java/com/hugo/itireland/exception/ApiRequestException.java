package com.hugo.itireland.exception;

public class ApiRequestException extends RuntimeException{
    public ApiRequestException(String message) {
        super(message);
    }
}
