package com.reborn.expense_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

public class BasicReq {
    private String fileNo;        // 單號
    private String fillFinDep;    // 部門
    private String fillId;        // 填單人id
    private Integer budgetYear;   // 預算年
    private String approveId;     // 審核主管 ID

    // Default constructor
    public BasicReq() {
    }

    // Constructor with all fields
    public BasicReq(String fileNo, String fillFinDep, String fillId, Integer budgetYear, String approveId) {
        this.fileNo = fileNo;
        this.fillFinDep = fillFinDep;
        this.fillId = fillId;
        this.budgetYear = budgetYear;
        this.approveId = approveId;
    }

    // Getters and Setters
    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getFillFinDep() {
        return fillFinDep;
    }

    public void setFillFinDep(String fillFinDep) {
        this.fillFinDep = fillFinDep;
    }

    public String getFillId() {
        return fillId;
    }

    public void setFillId(String fillId) {
        this.fillId = fillId;
    }

    public Integer getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(Integer budgetYear) {
        this.budgetYear = budgetYear;
    }

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    @Override
    public String toString() {
        return "BasicReq{" +
                "fileNo='" + fileNo + '\'' +
                ", fillFinDep='" + fillFinDep + '\'' +
                ", fillId='" + fillId + '\'' +
                ", budgetYear=" + budgetYear +
                ", approveId='" + approveId + '\'' +
                ", expDataList=" + expDataList +
                '}';
    }

    private List<ExpData> expDataList;

    public List<ExpData> getExpDataList() {
        return expDataList;
    }

    public void setExpDataList(List<ExpData> expDataList) {
        this.expDataList = expDataList;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExpData{
        @Schema(description = "報支費用明細檔ID")
        private String reimburseDetailId;

        @Schema(description = "支出代號")
        private String expCategory;

        @Schema(description = "支出明細")
        private String expDetail;

        @Schema(description = "摘要")
        private String note;

        @Schema(description = "請款總額")
        private BigDecimal amountReqOc;
    }
} 