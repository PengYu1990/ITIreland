package com.hugo.itireland.web.common;

import lombok.Data;

@Data
public class R<T>{

    private int status;

    private String message;

    private T data;

    private int totalPages;
    private Long totalElements;

    private int page;

    private R(int status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private R(T data, int totalPages, Long totalElements, int page){
        this.status = 200;
        this.message = "success";
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.page = page;
    }

    public static<T> R<T> success(T data){
        return new R(200, "success", data);
    }

    public static<T> R<T> error(int status, String message){
        return new R(status, message, null);
    }

    public static<T> R<T> success(T data, int totalPages, Long totalElements, int page){
        return new R(data, totalPages, totalElements, page);
    }

}
