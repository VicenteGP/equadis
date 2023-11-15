package com.equadis.bank.core.dto.accounting;

import com.equadis.bank.core.model.accounting.AccountType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

public record AccountBalanceResponse(
        @NotNull UUID identificationNumber,
        @NotNull AccountType type,
        double balance) {

    @Builder public AccountBalanceResponse{}
}
