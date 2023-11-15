package com.equadis.bank.core.dto.customer.request;

public record UpdateLegalEntityRequest(
        String username,
        String email,
        String companyName) {
}
