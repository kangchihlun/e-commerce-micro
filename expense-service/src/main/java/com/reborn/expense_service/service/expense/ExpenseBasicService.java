package com.reborn.expense_service.service.expense;

import com.reborn.expense_service.model.BasicReq;

public interface ExpenseBasicService {
    
    /**
     * Save basic expense data
     * @param req Basic expense request data
     * @return Result of the save operation
     */
    Object saveBasicData(BasicReq req);
} 