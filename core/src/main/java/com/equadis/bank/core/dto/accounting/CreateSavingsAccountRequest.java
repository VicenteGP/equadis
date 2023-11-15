package com.equadis.bank.core.dto.accounting;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record CreateSavingsAccountRequest(
        @NotNull String username,
        long initialBalance,
        float interest,
        boolean withdrawalEnable) {

    @Builder public CreateSavingsAccountRequest{}
}
