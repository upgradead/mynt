package com.mynt.gcash.calculators.implem;

import com.mynt.gcash.calculators.CostCalculator;
import com.mynt.gcash.config.RateConfig;
import com.mynt.gcash.model.RateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LargeParcelCalculator extends CostCalculator {

    @Autowired
    public LargeParcelCalculator(RateConfig rateConfig) {
        super(rateConfig);
    }

    @Override
    public String getType() {
        return RateEnum.LARGE.toString();
    }

    @Override
    public double calculate(double weight, double volume) {
        return rate() * volume;
    }
}
