package com.bytetcp.finalab.serve.cases.service;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Objects;

/**
 * excel处理器
 */
public interface IExcelHandler {

    boolean handlExcelSheet(Sheet sheet, Long caseId);

    /**
     *
     * @param sheet 要解析的sheet
     * @param row 第几行
     * @param col
     * @return
     */
    default int afterHasColumn(Sheet sheet, int row, int col) {
        Row r = sheet.getRow(row);
        int j = 0;
        CellType blank = CellType.BLANK;
        for (int i = col; Objects.nonNull(r.getCell(i))
                && !r.getCell(i).getCellTypeEnum().equals(blank); i++, j++) {
        }
        return j;
    }
}
