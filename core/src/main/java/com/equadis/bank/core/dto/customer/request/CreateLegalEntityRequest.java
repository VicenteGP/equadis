package com.equadis.bank.core.dto.customer.request;


import java.util.Objects;

public record CreateLegalEntityRequest(
        String username,
        String email,
        String companyName,
        String registrationNumber) {

    public CreateLegalEntityRequest {
        Objects.requireNonNull(username);
        Objects.requireNonNull(companyName);
        Objects.requireNonNull(registrationNumber);
    }
}
