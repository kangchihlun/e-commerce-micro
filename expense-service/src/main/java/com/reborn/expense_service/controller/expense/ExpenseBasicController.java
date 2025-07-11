package com.reborn.expense_service.controller.expense;
import com.reborn.expense_service.model.BasicReq;
import com.reborn.expense_service.service.expense.ExpenseBasicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("expense/basic")
@Tag(name = "Expense Basic", description = "Expense Basic API")
@Slf4j
@RequiredArgsConstructor
public class ExpenseBasicController {

    private final ExpenseBasicService expenseBasicService;

    /**
     * POST endpoint to save basic expense data
     * @param req Basic expense request data
     * @return Response with the result of the save operation
     */
    @Operation(summary = "Save Basic Expense Data", description = "Save basic expense data")
    @PostMapping("/save")
    public ResponseEntity<Object> saveBasicData(@RequestBody BasicReq req) {
        try {
            Object result = expenseBasicService.saveBasicData(req);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving basic data: " + e.getMessage());
        }
    }
} 