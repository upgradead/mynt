package com.mynt.gcash.calculators.implem;

import com.mynt.gcash.calculators.CostCalculator;
import com.mynt.gcash.config.RateConfig;
import com.mynt.gcash.exception.exception.CostValidationException;
import com.mynt.gcash.model.RateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RejectParcelCalculator extends CostCalculator {

    @Autowired
    public RejectParcelCalculator(RateConfig rateConfig) {
        super(rateConfig);
    }

    @Override
    public String getType() {
        return RateEnum.REJECT.toString();
    }

    @Override
    public double calculate(double weight, double volume) {
        if (weight > 50)  throw new CostValidationException("Rejected");
        return computeNext(weight, volume);
    }
}
