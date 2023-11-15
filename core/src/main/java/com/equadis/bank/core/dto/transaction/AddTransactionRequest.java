package com.equadis.bank.core.dto.transaction;

import com.equadis.bank.core.model.transaction.TransactionCategory;
import com.equadis.bank.core.model.transaction.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

public record AddTransactionRequest(
        @NotNull String username,
        @NotNull String accountIdentificationNumber,
        @NotNull TransactionCategory category,
        @NotNull TransactionType type,
        double amount,
        String description) {

    @Builder public AddTransactionRequest {}
}
