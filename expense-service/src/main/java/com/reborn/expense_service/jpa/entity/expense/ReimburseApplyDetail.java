package com.reborn.expense_service.jpa.entity.expense;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EXP_REIMBURSE_APPLY_DETAIL")
public class ReimburseApplyDetail {
    
    @Id
    @Column(name = "id")
    @Comment("報支明細檔Id")
    private String reimburseDetailId;

    @Column(name = "exp_category", length = 50)
    @Comment("支出代號")
    private String expCategory;

    @Column(name = "exp_detail", length = 200)
    @Comment("支出明細")
    private String expDetail;

    @Column(name = "note", length = 500)
    @Comment("摘要")
    private String note;

    @Column(name = "amount_req_oc", precision = 15, scale = 2)
    @Comment("請款總額")
    private BigDecimal amountReqOc;
}
