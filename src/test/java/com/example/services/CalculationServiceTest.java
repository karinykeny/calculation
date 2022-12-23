package com.example.services;

import com.example.domain.DataCalculation;
import com.example.domain.Result;
import com.example.domain.FinancingType;
import com.example.services.imp.CalculationServiceImp;
import com.example.tools.MessageProperty;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class CalculationServiceTest {

    private CalculationService service = new CalculationServiceImp();

    @Test
    public void calculateMonthlyInstallment() {
        Result resultado = this.service.calculateMonthlyInstallment(getCanculationBasis());
        Assert.assertNotNull(resultado);
        Assert.assertNotNull(resultado.getInstallment());
        Assert.assertEquals(new Double(13.3125),resultado.getInstallment());
    }

    @Test
    public void saveResult() throws IOException {
        Result resultado = new Result(new Double(13.3127), getCanculationBasis());
        resultado.setName("teste");
        resultado.setContact("000000001");
        String resposta = this.service.saveResult(resultado);

        Assert.assertNotNull(resposta);
        Assert.assertEquals(MessageProperty.getMessage(MessageProperty.MESSAGE_SAVE_SUCCESS),resposta);
    }

    private DataCalculation getCanculationBasis() {
        DataCalculation canculationBasis = new DataCalculation(
                FinancingType.EXTERNAL, 12, new BigDecimal(150.0));
        return canculationBasis;
    }
}