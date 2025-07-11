package com.reborn.expense_service.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.reborn.expense_service.jpa.entity.expense.ReimburseApply;


public interface ReimburseApplyRepo extends JpaRepository<ReimburseApply, String> , FileNoQueryable{
    default int existByFileNoStartWithExternal(String fileNoPrefix){
        return this.countByFileNoStartingWith(fileNoPrefix);
    }
    default boolean existByFileNoExternal(String fileNo){
        return this.existsByFileNo(fileNo);
    }
}
