
package com.reborn.expense_service.jpa.entity.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Schema(description="費用分攤明細")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldNameConstants
@Table(name = "EXP_APPORTION_DETAIL")
public class ApportionDetail{
    
    @Id
    @Column(name = "APPORTION_DETAIL_ID", length = 50)
    @Comment("費用分攤明細Id")
    private String apportionDetailId;

    @Column(name = "APPORTION_DEP", length = 100)
    @Comment("分攤部門")
    private String apportionDep;

    @Column(name = "EXP_CATEGORY", length = 50)
    @Comment("支出代號")
    private String expCategory;

    @Column(name = "EXP_DETAIL", length = 200)
    @Comment("支出明細")
    private String expDetail;

    @Column(name = "NOTE", length = 500)
    @Comment("摘要")
    private String note;

    @Column(name = "REIMBURSE_DETAIL_ID", length = 50)
    @Comment("報支明細Id")
    private String reimburseDetailId;

    @Column(name = "UPT_USER", length = 50)
    @Comment("更新人")
    private String uptUser;

    @Column(name = "UPT_TIME", length = 20)
    @Comment("更新時間")
    private String uptTime;
}