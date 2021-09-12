package com.mynt.gcash.service;

import com.mynt.gcash.model.Dimension;
import org.springframework.stereotype.Service;

@Service
public interface CostService {
    double calculate(Dimension dimension, String voucherCode);
}
