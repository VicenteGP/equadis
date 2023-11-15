package com.equadis.bank.core.dto.transaction;

import com.equadis.bank.core.model.transaction.TransactionCategory;
import com.equadis.bank.core.model.transaction.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


import java.time.OffsetDateTime;

public record TransactionSearchResponse(
        @NotNull OffsetDateTime timestamp,
        @NotNull TransactionCategory category,
        @NotNull TransactionType type,
        double amount,
        double currentBalance,
        String description
) {

    @Builder public TransactionSearchResponse {}
}
