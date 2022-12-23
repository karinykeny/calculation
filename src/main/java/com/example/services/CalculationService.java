package com.example.services;

import com.example.domain.DataCalculation;
import com.example.domain.Result;

import java.io.IOException;

public interface CalculationService {

    Result calculateMonthlyInstallment(DataCalculation canculationBasis);
    String saveResult(Result result) throws IOException;
}
