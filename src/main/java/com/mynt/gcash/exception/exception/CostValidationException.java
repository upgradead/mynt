package com.mynt.gcash.exception.exception;

import lombok.Data;

@Data
public class CostValidationException extends RuntimeException {

    private String message;

    public CostValidationException(String message) {
        this.message = message;
    }
}
