package com.hugo.itireland.web.util;

import lombok.Data;

@Data
public class R{
    private int status;
    private String message;

    private Object data;

    private R(int status, String message, Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public static R success(Object data){
        return new R(200, "success", data);
    }

    public static R error(int status, String message){
        return new R(status, message, null);
    }

}
