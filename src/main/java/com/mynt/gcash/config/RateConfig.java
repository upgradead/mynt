package com.mynt.gcash.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "rates")
@Data
public class RateConfig {

    private Map<String, Double> rate;

    public double getRuleRate(String ruleRule) {
        return getRate().entrySet().stream()
                .filter(x -> x.getKey().equals(ruleRule))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(0.0);
    }
}
