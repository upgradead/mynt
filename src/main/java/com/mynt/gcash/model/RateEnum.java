package com.mynt.gcash.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RateEnum {

    REJECT ("Reject"),
    HEAVY ("Heavy"),
    SMALL ("Small"),
    MEDIUM ("Medium"),
    LARGE ("Large");

    private String value;

    RateEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static RateEnum fromValue(String text) {
        for (RateEnum b : RateEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
