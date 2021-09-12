package com.mynt.gcash.model;

import lombok.Data;

@Data
public class Voucher {

    private String code;
    private double discount;
    private String expiry;

}
