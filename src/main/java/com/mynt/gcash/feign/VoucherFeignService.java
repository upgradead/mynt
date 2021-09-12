package com.mynt.gcash.feign;

import com.mynt.gcash.model.Voucher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Component
@FeignClient(name = "voucher", url = "${mynt.feign.url}")
public interface VoucherFeignService {

    @RequestMapping(method = RequestMethod.GET, value = "/voucher/{voucherCode}?key=apikey")
    public Optional<Voucher> getVoucher(@PathVariable("voucherCode") String voucherCode);

}
