package com.equadis.bank.core.controller.transaction;

import com.equadis.bank.core.dto.accounting.AccountBalanceResponse;
import com.equadis.bank.core.dto.accounting.ApplyInterestRequest;
import com.equadis.bank.core.dto.transaction.AddTransactionRequest;
import com.equadis.bank.core.dto.transaction.TransactionSearchFilter;
import com.equadis.bank.core.dto.transaction.TransactionSearchResponse;
import com.equadis.bank.core.service.AccountingService;
import com.equadis.bank.core.service.TransactionsService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping(path = "/transactions", produces = "application/json; charset=UTF-8")
public class TransactionController {

    private TransactionsService transactionsService;

    public TransactionController(TransactionsService transactionsService, AccountingService accountingService) {
        this.transactionsService = transactionsService;
    }

    @GetMapping
    @ResponseBody
    public Page<TransactionSearchResponse> getTransactions(TransactionSearchFilter transactionSearchFilter, @PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable) {
        return transactionsService.getTransactions(transactionSearchFilter, pageable);
    }

    @PostMapping
    @ResponseBody
    public AccountBalanceResponse addTransaction(AddTransactionRequest addTransactionRequest) {
        return transactionsService.addTransaction(addTransactionRequest);
    }

    @PostMapping(path = "/apply-interest")
    public void applyInterest(@RequestBody @Valid ApplyInterestRequest applyInterestRequest) {
        transactionsService.applyInterest(applyInterestRequest);
    }
}
