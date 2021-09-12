package com.mynt.gcash.calculators.implem;

import com.mynt.gcash.calculators.CostCalculator;
import com.mynt.gcash.config.RateConfig;
import com.mynt.gcash.model.RateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class HeavyParcelCalculator extends CostCalculator {

    @Autowired
    public HeavyParcelCalculator(RateConfig rateConfig) {
        super(rateConfig);
    }

    @Override
    public String getType() {
        return RateEnum.HEAVY.toString();
    }

    @Override
    public double calculate(double weight, double volume) {
        return (weight > 10) ?
                rate() * weight : computeNext(weight, volume);
    }
}
