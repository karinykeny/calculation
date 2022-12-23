package com.example.services.imp;

import com.example.domain.DataCalculation;
import com.example.domain.Result;
import com.example.domain.FinancingType;
import com.example.services.CalculationService;
import com.example.tools.MessageProperty;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CalculationServiceImp implements CalculationService {

    private static final String FILENAME = "src/main/resources/CalculosFile.xls";

    @Override
    public Result calculateMonthlyInstallment(DataCalculation dataCalculation) {

        Assert.isTrue((dataCalculation.getFinancingType().equals(FinancingType.INTERNAL)
                        && dataCalculation.getNumberMonthlyPayments() <= 48)
                        || (dataCalculation.getFinancingType().equals(FinancingType.EXTERNAL)),
                MessageProperty.getMessage(MessageProperty.MESSAGE_ERROR_TYPE_INTERNAL));

        try {
            Double result = dataCalculation.getVehicleValue().add(dataCalculation.getVehicleValue()
                    .multiply(new BigDecimal(dataCalculation.getFinancingType().getIncrement())))
                    .divide(BigDecimal.valueOf(dataCalculation.getNumberMonthlyPayments()),2, RoundingMode.HALF_UP)
                    .doubleValue();
            return new Result(result, dataCalculation);

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public String saveResult(Result result) throws FileNotFoundException {
        List<Result> results = getResultList();
        results.add(result);
        return this.saveCalculation(results);
    }

    public String saveCalculation(List<Result> results) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Calculos");

        int rowNum = 0;
        for (Result result: results) {
            rowNum = setRow(sheet, rowNum, result);
        }

        try {
            FileOutputStream out = new FileOutputStream(new File(FILENAME));
            workbook.write(out);
            out.close();
            return MessageProperty.getMessage(MessageProperty.MESSAGE_SAVE_SUCCESS);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return MessageProperty.getMessage(MessageProperty.MESSAGE_NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
            return MessageProperty.getMessage(MessageProperty.MESSAGE_ERROR_EDIT);
        }
    }

    private int setRow(HSSFSheet sheet, int rownum, Result dados) {
        Row row = sheet.createRow(rownum++);
        int cellnum = 0;

        Cell name = row.createCell(cellnum++);
        name.setCellValue(dados.getName());

        Cell contact = row.createCell(cellnum++);
        contact.setCellValue(dados.getContact());

        Cell installment = row.createCell(cellnum++);
        installment.setCellValue(dados.getInstallment());

        Cell vehicleValue = row.createCell(cellnum++);
        vehicleValue.setCellValue(String.valueOf(dados.getVehicleValue()));

        Cell typeFinanccing = row.createCell(cellnum++);
        typeFinanccing.setCellValue(dados.getFinancingType());

        Cell numberMonthlyPayments = row.createCell(cellnum++);
        numberMonthlyPayments.setCellValue(String.valueOf(dados.getNumberMonthlyPayments()));
        return rownum;
    }

    public List<Result> getResultList() throws FileNotFoundException {
        List<Result> resultList = new ArrayList<>();

        try {
            FileInputStream file = new FileInputStream(new File(FILENAME));
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheetProdutos = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheetProdutos.iterator();

            while (rowIterator.hasNext()) {
                Result result = new Result();
                resultList.add(result);
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                extracted(result, cellIterator);
            }
            file.close();
        } catch (Exception ex) {
            throw new FileNotFoundException(ex.getMessage());
        }
        return resultList;
    }

    private void extracted(Result result, Iterator<Cell> cellIterator) {
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            switch (cell.getColumnIndex()) {
                case 0:
                    result.setName(cell.getStringCellValue());
                    break;
                case 1:
                    result.setContact(cell.getStringCellValue());
                    break;
                case 2:
                    result.setInstallment(cell.getNumericCellValue());
                    break;
                case 3:
                    result.setVehicleValue(new BigDecimal(cell.getStringCellValue()));
                    break;
                case 4:
                    result.setFinancingType(cell.getStringCellValue());
                    break;
                case 5:
                    result.setNumberMonthlyPayments(Integer.parseInt(cell.getStringCellValue()));
                    break;
            }
        }
    }

}
