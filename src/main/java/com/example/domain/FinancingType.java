package com.example.domain;

import com.example.tools.MessageProperty;

public enum FinancingType {
    INTERNAL("type.financing.name.internal", "type.financing.increment.internal"),
    EXTERNAL("type.financing.name.external", "type.financing.increment.external");

    private String name;
    private String increment;


    FinancingType(String name, String increment) {
        this.name = name;
        this.increment = increment;
    }

    public String getName() {
        return MessageProperty.getMessage(name);
    }

    public String getIncrement() {
        return MessageProperty.getMessage(increment);
    }
}
