package com.hugo.itireland.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;


@Data
@AllArgsConstructor
public class ApiExceptionResponse {

    private final String path;
    private final String message;
    private final int status;
    private final LocalDateTime localDateTime;


}
