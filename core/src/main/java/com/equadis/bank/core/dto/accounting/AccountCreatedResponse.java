package com.equadis.bank.core.dto.accounting;

import lombok.Builder;

import java.util.UUID;

public record AccountCreatedResponse(UUID identificationNumber) {

    @Builder public AccountCreatedResponse {}
}
