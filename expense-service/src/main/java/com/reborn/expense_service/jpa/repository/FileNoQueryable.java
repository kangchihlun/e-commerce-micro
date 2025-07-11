package com.reborn.expense_service.jpa.repository;

public interface FileNoQueryable {
    int countByFileNoStartingWith(String fileNoPrefix);
    boolean existsByFileNo(String fileNo);
}
