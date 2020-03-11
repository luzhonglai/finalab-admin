package com.bytetcp.finalab.common.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.formula.eval.FunctionEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

/**
 * @author chenchuan@autohome.com.cn
 * @create 2019-04-27-下午1:12
 * @description
 */
public class NormInvTest {

    @Test
    public void readExcel() throws IOException {
        FunctionEval.registerFunction("NORMINV", new NormInv());
        String filePath = "/Users/chenchuan/Downloads/test.xlsx";
        String sheetName = "Sheet1";
        //String suffix = fileSuffix(filePath);
        //Preconditions.checkArgument("xlsx".equalsIgnoreCase(suffix), "只支持excel2007 格式");
        File excelFile = new File(filePath);
        if (!excelFile.exists()) {
            throw new FileNotFoundException("对应的excel文件不存在或者无权限读取！" + filePath);
        }

        InputStream stream = new FileInputStream(excelFile);
        Workbook wb = new XSSFWorkbook(stream);
        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
        Sheet st = wb.getSheet(sheetName);
        if (null == st) {
            throw new FileNotFoundException("对应的Sheet页面不存在" + sheetName);
        }

        for (int i = 0; i <= st.getLastRowNum(); i++) {
            Row row = st.getRow(i);
            if (row == null) {
                continue;
            }
            for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
                Cell cell = row.getCell(columnIndex);
                if(CellType.FORMULA == cell.getCellTypeEnum()){
                    System.out.println(cell.getCellFormula());
                }

                CellValue cellValue = evaluator.evaluate(cell);

                System.out.println(cellValue);
                System.out.println(cellValue.getCellTypeEnum());
                System.out.println(cellValue.getNumberValue());
//                String value = "";
//                try {
//                    value = String.valueOf(cell.getNumericCellValue());
//                } catch (IllegalStateException e) {
//                    value = String.valueOf(cell.getRichStringCellValue());
//                }
//                System.out.println(value);
            }
        }
    }

    @Test
    public void editExcel() throws IOException {
        String filePath = "/Users/chenchuan/Downloads/test.xlsx";
        String sheetName = "Sheet2";
        File excelFile = new File(filePath);
        if (!excelFile.exists()) {
            throw new FileNotFoundException("对应的excel文件不存在或者无权限读取！" + filePath);
        }
        InputStream stream = new FileInputStream(excelFile);
        Workbook wb = new XSSFWorkbook(stream);
        Sheet st = wb.getSheet(sheetName);
        Row row = st.getRow(0);
        Cell cell = row.getCell(0);
        cell.setCellValue(0.1304373d);
        FileOutputStream excelFileOutPutStream = new FileOutputStream(filePath);//写数据到这个路径上
        wb.write(excelFileOutPutStream);
        excelFileOutPutStream.flush();
        excelFileOutPutStream.close();
        System.out.println(cell);
    }

}
