package com.hugo.itireland.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class ApiExceptionResponse {

    private final String path;
    private final String message;
    private final int status;
    private final LocalDateTime localDateTime;


}
