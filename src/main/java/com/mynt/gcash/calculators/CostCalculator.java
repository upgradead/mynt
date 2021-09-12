package com.mynt.gcash.calculators;

import com.mynt.gcash.config.RateConfig;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public abstract class CostCalculator {

    private RateConfig rateConfig;
    private CostCalculator next;

    public CostCalculator (RateConfig rateConfig){
        this.rateConfig = rateConfig;
    }

    public abstract String getType();

    public abstract double calculate(double weight, double volume);

    public CostCalculator linkWith(CostCalculator next) {
        this.next = next;
        return next;
    }

    protected double rate() {
        return rateConfig.getRuleRate(getType());
    }

    protected double computeNext(double weight, double volume) {
        return (next == null) ?
                0 : next.calculate(weight, volume);
    }
}
