package com.mynt.gcash.controller;

import com.mynt.gcash.exception.exception.CostValidationException;
import com.mynt.gcash.model.CostRequest;
import com.mynt.gcash.model.Dimension;
import com.mynt.gcash.service.CostService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@Slf4j
public class CostController {

    private CostService costService;

    @Autowired
    public CostController(CostService costService) {
        this.costService = costService;
    }

    @PostMapping(value = "/calculate",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Double> calculate(@ApiParam(value = "", required = true) @Valid @RequestBody CostRequest costRequest) {

        if (costRequest.getDimention().getWeight() == 0) { // Sample validation to demonstrate error handling, validation already added in swagger file for some fields
            throw new CostValidationException("Weight can't be null");
        }

        return ResponseEntity.status(HttpStatus.OK).body(costService.calculate(costRequest.getDimention(), costRequest.getVoucher()));
    }
}
