package com.bytetcp.finalab.common.utils.poi;

import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.config.Global;
import com.bytetcp.finalab.common.exception.BusinessException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lenovo on 2020/5/13.
 */
public class ExpExcelUtil {
    private static final Logger log = LoggerFactory.getLogger(ExpExcelUtil.class);

    private static String filename = UUID.randomUUID().toString() + "_DetailsExport" + ".xlsx";


    public static AjaxResult expExcel(List<String[]> dataset,List<String[]> dataset2) {
        try {

            //表头
            String[] handers1 = {"TraderId", "阶段", "成交时间", "账号", "交易标的", "成交价格", "成交数量", "成本金额", "类型"};
            String[] handers2 = {"TraderId", "账号", "已实现盈亏", "未实现盈亏", "交易费", "总罚款", "总盈亏"};

            //对象
            ExcelExp e1 = new ExcelExp("成交明细", handers1, dataset);
            ExcelExp e2 = new ExcelExp("交易盈亏", handers2, dataset2);

            List<ExcelExp> mysheet = new ArrayList<ExcelExp>();
            mysheet.add(e1);
            mysheet.add(e2);

            AjaxResult ajaxResult = exportManySheetExcel(getAbsoluteFile(filename), mysheet);//生成sheet
            return ajaxResult;
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
            throw new BusinessException("导出Excel失败，请联系网站管理员！");
        }
    }

    public static AjaxResult exportManySheetExcel(String file, List<ExcelExp> mysheets) {
        SXSSFWorkbook wb = new SXSSFWorkbook(500);//创建工作薄
        List<ExcelExp> sheets = mysheets;
        Cell cell = null; // 产生单元格
        //字体样式
        Font font = wb.createFont();
        // 粗体显示
        font.setBold(true);

        for (ExcelExp excel : sheets) {
            //新建一个sheet
            Sheet sheet = wb.createSheet(excel.getFileName());//获取该sheet名称
            Row row = sheet.createRow(0);

            cell = row.createCell(0);
            cell.setCellType(CellType.STRING);
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setFont(font);
            cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());

            String[] handers = excel.getHanders();//获取sheet的标题名
            Row rowFirst = sheet.createRow(0);//第一个sheet的第一行为标题
            //写标题
            for (int i = 0; i < handers.length; i++) {
                //获取第一行的每个单元格
                Cell cells = rowFirst.createCell(i);
                //往单元格里写数据
                cells.setCellValue(handers[i]);
                cells.setCellStyle(cellStyle); //加样式
                sheet.setColumnWidth(i, 4000); //设置每列的列宽
            }

            //写数据集
            List<String[]> dataset = excel.getDataset();
            for (int i = 0; i < dataset.size(); i++) {
                String[] data = dataset.get(i);//获取该对象
                //创建数据行
                row = sheet.createRow(i + 1);
                for (int j = 0; j < data.length; j++) {
                    //设置对应单元格的值
                    row.createCell(j).setCellValue(data[j]);
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cellStyle.setWrapText(true);
                    cell.setCellStyle(cellStyle);
                }
            }
        }
        // 写文件
        try {
            FileOutputStream out = new FileOutputStream(new File(file));
            out.flush();
            wb.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.success(filename);
    }


    /**
     * 获取下载路径
     *
     * @param filename 文件名称
     */
    public static String getAbsoluteFile(String filename) {
        String downloadPath = Global.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }
}
