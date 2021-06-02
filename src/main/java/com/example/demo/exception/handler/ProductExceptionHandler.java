package com.example.demo.exception.handler;

import com.example.demo.exception.InternalServerErrorException;
import com.example.demo.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse handleNotFoundException(NotFoundException notFoundException) {
        return new ErrorMessageResponse(404, notFoundException.getId());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessageResponse handleInternalServerErrorException(InternalServerErrorException internalServerErrorException) {
        return new ErrorMessageResponse(internalServerErrorException.getCode(), internalServerErrorException.getMessage());
    }
}
