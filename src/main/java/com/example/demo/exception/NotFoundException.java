package com.example.demo.exception;

public class NotFoundException extends RuntimeException {
    private String idResource;
    public NotFoundException(String idResource) {
        this.idResource = idResource;
    }
    public String getId() {
        return idResource;
    }
}
