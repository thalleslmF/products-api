package com.example.demo.exception.handler;

import com.example.demo.exception.InternalServerErrorException;
import com.example.demo.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse handleNotFoundException(NotFoundException notFoundException) {
        return new ErrorMessageResponse(HttpStatus.NOT_FOUND.value(), notFoundException.getId());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessageResponse handleInternalServerErrorException(InternalServerErrorException internalServerErrorException) {
        return new ErrorMessageResponse(internalServerErrorException.getCode(), internalServerErrorException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException) {
        var errors = methodArgumentNotValidException.getFieldErrors();
        var formatedErrorList = errors.stream().map(
                        error ->String.format("Field '%s'  with error: '%s' ", error.getField(), error.getDefaultMessage())
                ).collect(joining(","));
        return new ErrorMessageResponse(HttpStatus.BAD_REQUEST.value(), formatedErrorList);
    }
}
