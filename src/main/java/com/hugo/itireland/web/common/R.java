package com.hugo.itireland.web.common;

import lombok.Data;

@Data
public class R<T>{

    private int status;

    private String message;

    private T data;

    private R(int status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public static<T> R<T> success(T data){
        return new R(200, "success", data);
    }

    public static<T> R<T> error(int status, String message){
        return new R(status, message, null);
    }

}
