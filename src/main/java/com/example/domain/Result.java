package com.example.domain;

import java.math.BigDecimal;

public class Result {
    private String name;
    private String contact;
    private Double installment;
    private String financingType;
    private int numberMonthlyPayments;
    private BigDecimal vehicleValue;

    public Result(Double installment, DataCalculation data) {
        this.financingType = data.getFinancingType().getName();
        this.numberMonthlyPayments = data.getNumberMonthlyPayments();
        this.vehicleValue = data.getVehicleValue();
        this.installment = installment;
    }

    public Result() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Double getInstallment() {
        return installment;
    }

    public void setInstallment(Double installment) {
        this.installment = installment;
    }

    public String getFinancingType() {
        return financingType;
    }

    public void setFinancingType(String financingType) {
        this.financingType = financingType;
    }

    public int getNumberMonthlyPayments() {
        return numberMonthlyPayments;
    }

    public void setNumberMonthlyPayments(int numberMonthlyPayments) {
        this.numberMonthlyPayments = numberMonthlyPayments;
    }

    public BigDecimal getVehicleValue() {
        return vehicleValue;
    }

    public void setVehicleValue(BigDecimal vehicleValue) {
        this.vehicleValue = vehicleValue;
    }
}
