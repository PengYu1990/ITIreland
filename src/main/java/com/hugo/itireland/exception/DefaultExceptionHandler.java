package com.hugo.itireland.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(ResourceNotFoundException e,
                                                    HttpServletRequest request) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(InsufficientAuthenticationException e,
                                                    HttpServletRequest request) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(BadCredentialsException e,
                                                    HttpServletRequest request) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(ApiRequestException e,
                                                                HttpServletRequest request) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(ValidationException e,
                                                                HttpServletRequest request) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(HttpServletRequest request,ExpiredJwtException e){
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handleException(Exception e,
                                                    HttpServletRequest request) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
