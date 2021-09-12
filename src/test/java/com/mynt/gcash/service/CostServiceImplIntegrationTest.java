package com.mynt.gcash.service;

import com.mynt.gcash.calculators.implem.*;
import com.mynt.gcash.config.RateConfig;
import com.mynt.gcash.exception.exception.CostValidationException;
import com.mynt.gcash.feign.VoucherFeignService;
import com.mynt.gcash.model.Dimension;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HeavyParcelCalculator.class, LargeParcelCalculator.class, MediumParcelCalculator.class,
        RejectParcelCalculator.class, SmallParcelCalculator.class, CostServiceImpl.class, VoucherFeignService.class})
@EnableConfigurationProperties(value = RateConfig.class)
public class CostServiceImplIntegrationTest {

    @Autowired
    private CostServiceImpl costService;

    @MockBean
    private VoucherFeignService voucherFeignService;

    private Dimension dimension;

    @Before
    public void initBeforeClass() {
        dimension = new Dimension();
        dimension.setWeight(0.0);
        dimension.setWidth(0.0);
        dimension.setHeight(0.0);
        dimension.setLength(0.0);
    }

    @Test
    public void whenWeightGreaterThan50ThenRejectNoVoucherThrowError() {
        dimension.setWeight(55.0);
        try {
            costService.calculate(dimension, "");
            Assert.fail();
        } catch (CostValidationException e) {
            Assert.assertEquals("Rejected", e.getMessage());
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void whenWeightGreaterThan10ThenHeavyParcelNoVoucher() {
        dimension.setWeight(15.0);
        Assert.assertEquals(300, costService.calculate(dimension, ""), .001);
    }

    @Test
    public void whenWeightLessThan10AndVolumeLessThan1500NoVoucher() {
        dimension.setWeight(9.0);
        dimension.setWidth(1.0);
        dimension.setLength(2.0);
        dimension.setHeight(3.0);
        Assert.assertEquals(.18, costService.calculate(dimension, ""), .001);
    }

    @Test
    public void whenWeightLessThan10AndVolumeLessThan2500NoVoucher() {
        dimension.setWeight(9.0);
        dimension.setWidth(1.0);
        dimension.setLength(1.0);
        dimension.setHeight(2000.0);
        Assert.assertEquals(80, costService.calculate(dimension, ""), .001);
    }

    @Test
    public void whenWeightLessThan10AndVolumeGreaterhan2500NoVoucher() {
        dimension.setWeight(9.0);
        dimension.setWidth(1.0);
        dimension.setLength(1.0);
        dimension.setHeight(2501.0);
        Assert.assertEquals(125.05, costService.calculate(dimension, ""), .001);
    }

}