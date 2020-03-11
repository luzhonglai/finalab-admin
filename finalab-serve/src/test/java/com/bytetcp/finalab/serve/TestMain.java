package com.bytetcp.finalab.serve;

import com.bytetcp.finalab.common.enums.SheetName;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class TestMain {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        File file = new File("G:/tableDesk/股票_大宗交易实列_Load.xlsx");
        InputStream inputStream = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheet(SheetName.LIQUIDATION.getSheetName());
        System.out.println(sheet.getRow(1).getCell(1));
        System.out.println(afterHasColumn(sheet, 2, 3));

    }

    private static int afterHasColumn(Sheet sheet, int row, int col) {
        Row r = sheet.getRow(row);
        CellType blank = CellType.BLANK;
        
        int j = 0;
        for (int i = col; Objects.nonNull(r.getCell(i)) && !r.getCell(i).getCellTypeEnum().equals(blank); i++, j++) {

        }
        FormulaEvaluator evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
        CellValue evaluate = evaluator.evaluate(r.getCell(3));
        System.out.println(evaluate +  "   666  " + evaluate.formatAsString());
        System.out.println(evaluate.getCellTypeEnum());


        return j;
    }
}
