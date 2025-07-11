package com.reborn.expense_service.jpa.entity.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Schema(name = "ReimburseApply", description = "報支費用主檔")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "EXP_REIMBURSE_APPLY")
public class ReimburseApply {
    
    @Id
    @Column(name = "FILE_NO", length = 50)
    @Comment("報支單號")
    private String fileNo;

    @Column(name = "BUDGET_YEAR")
    @Comment("預算年度")
    private Integer budgetYear;

    @Column(name = "FILL_FIN_DEP", length = 100)
    @Comment("填單部門")
    private String fillFinDep;

    @Column(name = "FILL_ID", length = 50)
    @Comment("填單人Id")
    private String fillId;

    @Column(name = "FILL_NAME", length = 100)
    @Comment("填單人姓名")
    private String fillName;

    @Column(name = "FILL_DATE", length = 20)
    @Comment("填單日期")
    private String fillDate;

    @Column(name = "APPLY_FIN_DEP", length = 100)
    @Comment("申請部門")
    private String applyFinDep;

    @Column(name = "APPLY_ID", length = 50)
    @Comment("申請人Id")
    private String applyId;

    @Column(name = "APPLY_NAME", length = 100)
    @Comment("申請人姓名")
    private String applyName;
}
