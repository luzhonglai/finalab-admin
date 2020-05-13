package com.bytetcp.finalab.common.utils.poi;

import java.util.List;

/**
 * Created by lenovo on 2020/5/13.
 */
public class ExcelExp {

        private String fileName;// sheet的名称
        private String[] handers;// sheet里的标题
        private List<String[]> dataset;// sheet里的数据集

        public ExcelExp(String fileName, String[] handers, List<String[]> dataset) {
            this.fileName = fileName;
            this.handers = handers;
            this.dataset = dataset;
        }

    public String[] getHanders() {
        return handers;
    }

    public void setHanders(String[] handers) {
        this.handers = handers;
    }

    public List<String[]> getDataset() {
        return dataset;
    }

    public void setDataset(List<String[]> dataset) {
        this.dataset = dataset;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
