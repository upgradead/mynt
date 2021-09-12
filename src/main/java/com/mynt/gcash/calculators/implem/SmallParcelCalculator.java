package com.mynt.gcash.calculators.implem;

import com.mynt.gcash.calculators.CostCalculator;
import com.mynt.gcash.config.RateConfig;
import com.mynt.gcash.model.RateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmallParcelCalculator extends CostCalculator {

    @Autowired
    public SmallParcelCalculator(RateConfig rateConfig) {
        super(rateConfig);
    }

    @Override
    public String getType() {
        return RateEnum.SMALL.toString();
    }

    @Override
    public double calculate(double weight, double volume) {
        return (volume < 1500) ?
                rate() * volume : computeNext(weight, volume);
    }
}
