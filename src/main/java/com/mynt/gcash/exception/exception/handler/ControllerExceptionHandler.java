package com.mynt.gcash.exception.exception.handler;

import com.mynt.gcash.exception.exception.CostValidationException;
import com.mynt.gcash.exception.exception.model.ErrorMessage;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorMessage validationExceptionHandler(CostValidationException e) {
        return new ErrorMessage(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Validation error " + e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorMessage mainExceptionHandler(Exception e) {
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "Unexpected exception " + e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorMessage feignExceptionHandler(FeignException.FeignClientException e) {
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "Feign Exception encountered " + e.getMessage());

    }
}
