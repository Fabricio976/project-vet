package com.project.exeptions;

public class RgNotFoundException extends RuntimeException {
    public RgNotFoundException(String message) {
        super(message);
    }
}