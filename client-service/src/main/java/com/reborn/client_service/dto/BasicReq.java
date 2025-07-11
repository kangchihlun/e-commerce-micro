package com.reborn.client_service.dto;

import java.math.BigDecimal;
import java.util.List;

public class BasicReq {
    private String fileNo;        // 單號
    private String fillFinDep;    // 部門
    private String fillId;        // 填單人id
    private Integer budgetYear;   // 預算年
    private String approveId;     // 審核主管 ID
    private List<ExpData> expDataList;

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

    public List<ExpData> getExpDataList() {
        return expDataList;
    }

    public void setExpDataList(List<ExpData> expDataList) {
        this.expDataList = expDataList;
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

    public static class ExpData {
        private String reimburseDetailId;
        private String expCategory;
        private String expDetail;
        private String note;
        private BigDecimal amountReqOc;

        // Default constructor
        public ExpData() {
        }

        // Constructor with all fields
        public ExpData(String reimburseDetailId, String expCategory, String expDetail, String note, BigDecimal amountReqOc) {
            this.reimburseDetailId = reimburseDetailId;
            this.expCategory = expCategory;
            this.expDetail = expDetail;
            this.note = note;
            this.amountReqOc = amountReqOc;
        }

        // Getters and Setters
        public String getReimburseDetailId() {
            return reimburseDetailId;
        }

        public void setReimburseDetailId(String reimburseDetailId) {
            this.reimburseDetailId = reimburseDetailId;
        }

        public String getExpCategory() {
            return expCategory;
        }

        public void setExpCategory(String expCategory) {
            this.expCategory = expCategory;
        }

        public String getExpDetail() {
            return expDetail;
        }

        public void setExpDetail(String expDetail) {
            this.expDetail = expDetail;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public BigDecimal getAmountReqOc() {
            return amountReqOc;
        }

        public void setAmountReqOc(BigDecimal amountReqOc) {
            this.amountReqOc = amountReqOc;
        }

        @Override
        public String toString() {
            return "ExpData{" +
                    "reimburseDetailId='" + reimburseDetailId + '\'' +
                    ", expCategory='" + expCategory + '\'' +
                    ", expDetail='" + expDetail + '\'' +
                    ", note='" + note + '\'' +
                    ", amountReqOc=" + amountReqOc +
                    '}';
        }
    }
} 