package com.tibco.omsui.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtility {

    private Workbook workbook;

    public ExcelUtility(String filePath) throws IOException, InvalidFormatException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        this.workbook = new XSSFWorkbook(fileInputStream);
    }

    public List<String> getRowData(String sheetName, int rowNum){
        List<String> rowData = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        for(Cell cell : row){
            switch (cell.getCellType()) {
                case STRING:
                    rowData.add(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        rowData.add(cell.getDateCellValue().toString());
                    } else {
                        double numericValue = cell.getNumericCellValue();
                        if (numericValue == (long) numericValue) {
                            rowData.add(String.valueOf((long) numericValue)); // Remove decimal for whole numbers
                        } else {
                            rowData.add(String.valueOf(numericValue));
                        }
                    }
            }
        }
        return rowData;
    }

}
