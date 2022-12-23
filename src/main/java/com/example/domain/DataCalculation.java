package com.example.domain;


import java.math.BigDecimal;

public class DataCalculation {

    private FinancingType financingType;
    private int numberMonthlyPayments;
    private BigDecimal vehicleValue;

    public DataCalculation(FinancingType financingType, int numberMonthlyPayments, BigDecimal vehicleValue) {
        this.financingType = financingType;
        this.numberMonthlyPayments = numberMonthlyPayments;
        this.vehicleValue = vehicleValue;
    }

    public DataCalculation() {
    }

    public FinancingType getFinancingType() {
        return financingType;
    }

    public void setFinancingType(FinancingType financingType) {
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
