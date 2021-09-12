package com.mynt.gcash.calculators.implem;

import com.mynt.gcash.calculators.CostCalculator;
import com.mynt.gcash.config.RateConfig;
import com.mynt.gcash.model.RateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MediumParcelCalculator extends CostCalculator {

    @Autowired
    public MediumParcelCalculator(RateConfig rateConfig) {
        super(rateConfig);
    }

    @Override
    public String getType() {
        return RateEnum.MEDIUM.toString();
    }

    @Override
    public double calculate(double weight, double volume) {
        return (volume < 2500) ?
                rate() * volume : computeNext(weight, volume);
    }
}
