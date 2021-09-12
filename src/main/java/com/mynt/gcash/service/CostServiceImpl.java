package com.mynt.gcash.service;

import com.mynt.gcash.calculators.CostCalculator;
import com.mynt.gcash.calculators.implem.*;
import com.mynt.gcash.feign.VoucherFeignService;
import com.mynt.gcash.model.Dimension;
import com.mynt.gcash.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CostServiceImpl implements CostService {

    private RejectParcelCalculator rejectParcelCalculator;
    private HeavyParcelCalculator heavyParcelCalculator;
    private SmallParcelCalculator smallParcelCalculator;
    private MediumParcelCalculator mediumParcelCalculator;
    private LargeParcelCalculator largeParcelCalculator;
    private VoucherFeignService voucherFeignService;


    @Autowired
    public CostServiceImpl(RejectParcelCalculator rejectParcelCalculator, HeavyParcelCalculator heavyParcelCalculator,
                           SmallParcelCalculator smallParcelCalculator, MediumParcelCalculator mediumParcelCalculator,
                           LargeParcelCalculator largeParcelCalculator, VoucherFeignService voucherFeignService) {
        this.rejectParcelCalculator = rejectParcelCalculator;
        this.heavyParcelCalculator = heavyParcelCalculator;
        this.smallParcelCalculator = smallParcelCalculator;
        this.mediumParcelCalculator = mediumParcelCalculator;
        this.largeParcelCalculator = largeParcelCalculator;
        this.voucherFeignService = voucherFeignService;
    }

    @Override
    public double calculate(Dimension dimension, String voucherCode) {

        double weight = dimension.getWeight();
        double volume = dimension.getWidth() * dimension.getHeight() * dimension.getLength();

        // Added by priority
        CostCalculator costCalculator = rejectParcelCalculator;
        costCalculator.linkWith(heavyParcelCalculator)
                .linkWith(smallParcelCalculator)
                .linkWith(mediumParcelCalculator)
                .linkWith(largeParcelCalculator);

        double cost = costCalculator.calculate(weight, volume);

        if (!StringUtils.isEmpty(voucherCode)) {
            Optional<Voucher> voucher = voucherFeignService.getVoucher(voucherCode);
            return voucher.map(v -> {
                double discount = v.getDiscount() / 100;
                return cost - (cost * discount);
            }).orElse(0.0);
        }

        return cost;
    }
}
