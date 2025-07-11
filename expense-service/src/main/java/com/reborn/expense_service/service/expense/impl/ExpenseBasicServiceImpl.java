package com.reborn.expense_service.service.expense.impl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.reborn.expense_service.model.BasicReq;
import com.reborn.expense_service.model.BasicReq.ExpData;
import com.reborn.expense_service.service.expense.ExpenseBasicService;
import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.reborn.expense_service.jpa.entity.expense.ReimburseApplyDetail;
import com.reborn.expense_service.jpa.entity.expense.ApportionDetail;
import com.reborn.expense_service.jpa.entity.expense.ReimburseApply;
import com.reborn.expense_service.jpa.repository.ReimburseApplyRepo;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpenseBasicServiceImpl implements ExpenseBasicService {
    
    private final ReimburseApplyRepo reimburseApplyRepo;

    @Override
    @Transactional
    public String saveBasicData(BasicReq req) {
        List<ReimburseApplyDetail> reimburseApplyDetails = new ArrayList<>();
        List<ApportionDetail> apportionDetails = new ArrayList<>();
        String fileNo;
        try{
            ReimburseApply reimburseApplyEntity = createReimburseApply(req);
            fileNo = reimburseApplyEntity.getFileNo();
            if(req.getExpDataList() != null){
                for(ExpData expData : req.getExpDataList()){
                    ReimburseApplyDetail reimburseApplyDetail = new ReimburseApplyDetail();
                    // 設置報支明細檔Id (使用檔案編號加上時間戳或序號)
                    String detailId = fileNo + "_" + System.currentTimeMillis();
                    reimburseApplyDetail.setReimburseDetailId(detailId);
                    reimburseApplyDetail.setExpCategory(expData.getExpCategory());
                    reimburseApplyDetail.setExpDetail(expData.getExpDetail());
                    reimburseApplyDetail.setNote(expData.getNote());
                    reimburseApplyDetail.setAmountReqOc(expData.getAmountReqOc());
                    
                    reimburseApplyDetails.add(reimburseApplyDetail);
                }
            }
            reimburseApplyRepo.save(reimburseApplyEntity);
        }catch(Exception e){
            log.error("Error saving basic data: {}", e.getMessage());
            throw new RuntimeException("Error saving basic data");
        }
        return "Basic data saved successfully for file: " + req.getFileNo();
    }

    private ReimburseApply createReimburseApply(BasicReq req){
        String user = "root";
        boolean isNew = StringUtils.isBlank(req.getFileNo());
        
        ReimburseApply reimburseApply;
        if (isNew) {
            // 如果是新記錄，生成一個新的檔案編號
            String newFileNo = generateFileNo();
            reimburseApply = new ReimburseApply();
            reimburseApply.setFileNo(newFileNo);
        } else {
            // 如果是現有記錄，從資料庫查詢
            reimburseApply = reimburseApplyRepo.findById(req.getFileNo()).orElse(null);
            if (reimburseApply == null) {
                // 如果找不到現有記錄，創建新的
                reimburseApply = new ReimburseApply();
                reimburseApply.setFileNo(req.getFileNo());
            }
        }
        
        setCommonReimburseApplyFields(reimburseApply, req);
        setAuditFields(reimburseApply, user);
        return reimburseApply;
    }
    
    /**
     * 生成新的檔案編號
     * @return 新的檔案編號
     */
    private String generateFileNo() {
        // 使用時間戳和隨機數生成唯一檔案編號
        String timestamp = String.valueOf(System.currentTimeMillis());
        String random = String.valueOf((int)(Math.random() * 1000));
        return "EXP" + timestamp.substring(timestamp.length() - 8) + random;
    }

    /**
     * 設置 ReimburseApply 的通用欄位
     * @param reimburseApply 報支申請實體
     * @param req 基本請求資料
     */
    private void setCommonReimburseApplyFields(ReimburseApply reimburseApply, BasicReq req) {
        // 注意：檔案編號已在 createReimburseApply 方法中設置，這裡不需要重複設置
        
        // 設置預算年度
        if (req.getBudgetYear() != null) {
            reimburseApply.setBudgetYear(req.getBudgetYear());
        }
        
        // 設置填單部門
        if (StringUtils.isNotBlank(req.getFillFinDep())) {
            reimburseApply.setFillFinDep(req.getFillFinDep());
        }
        
        // 設置填單人ID
        if (StringUtils.isNotBlank(req.getFillId())) {
            reimburseApply.setFillId(req.getFillId());
        }
        
        // 設置申請部門 (假設與填單部門相同)
        if (StringUtils.isNotBlank(req.getFillFinDep())) {
            reimburseApply.setApplyFinDep(req.getFillFinDep());
        }
        
        // 設置申請人ID (假設與填單人ID相同)
        if (StringUtils.isNotBlank(req.getFillId())) {
            reimburseApply.setApplyId(req.getFillId());
        }
        
        // 設置填單日期 (當前日期)
        String currentDate = java.time.LocalDate.now().toString();
        reimburseApply.setFillDate(currentDate);
    }

    /**
     * 設置審計欄位
     * @param reimburseApply 報支申請實體
     * @param user 當前用戶
     */
    private void setAuditFields(ReimburseApply reimburseApply, String user) {
        // 設置填單人姓名 (這裡假設與用戶ID相同，實際應該從用戶服務獲取)
        if (StringUtils.isNotBlank(user)) {
            reimburseApply.setFillName(user);
            reimburseApply.setApplyName(user);
        }
        
        // 設置更新時間
        String currentTime = java.time.LocalDateTime.now().toString();
        // 注意：ReimburseApply 實體中沒有 uptTime 欄位，如果需要可以添加
        // 或者使用其他時間欄位
    }
} 