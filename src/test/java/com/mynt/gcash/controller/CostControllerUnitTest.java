package com.mynt.gcash.controller;

import com.mynt.gcash.exception.exception.CostValidationException;
import com.mynt.gcash.model.CostRequest;
import com.mynt.gcash.model.Dimension;
import com.mynt.gcash.service.CostService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;

public class CostControllerUnitTest {

    private CostService costService;
    private CostController costController;

    private static Dimension dimension;

    private static CostRequest costRequest;

    @BeforeClass
    public static void initBeforeClass() {
        costRequest = new CostRequest();
        dimension = new Dimension();
        dimension.setWeight(3.0);
        costRequest.setDimention(dimension);
        costRequest.setVoucher("");
    }

    @Before
    public void init() {
        costService = Mockito.mock(CostService.class);
        costController = new CostController(costService);
    }

    @Test
    public void calculateHappyPath() {
        double expectedResult = 25.0;
        Mockito.when(costService.calculate(dimension, "")).thenReturn(expectedResult);
        ResponseEntity<Double> calculate = costController.calculate(costRequest);

        assertEquals(HttpStatus.OK, calculate.getStatusCode());
        assertEquals(Double.valueOf(expectedResult), calculate.getBody());
        Mockito.verify(costService, atLeastOnce()).calculate(Mockito.any(Dimension.class), Mockito.anyString());
    }

    @Test
    public void whenWeightEqualToZeroThenThrowCostValidationException() {

        Mockito.when(costService.calculate(dimension, "")).thenThrow(CostValidationException.class);

        try {
            costController.calculate(costRequest);
            Assert.fail();
        } catch (CostValidationException e) {
            Assert.assertNotNull(e);
        } catch (Exception e) {
            Assert.fail();
        }

        Mockito.verify(costService, atLeastOnce()).calculate(Mockito.any(Dimension.class), Mockito.anyString());
    }
}