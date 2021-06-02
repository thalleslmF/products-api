package com.example.demo.exception;

public class InternalServerErrorException extends RuntimeException {
    private int code;
    private String message;

    public InternalServerErrorException(String message) {
        this.code = 500;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
