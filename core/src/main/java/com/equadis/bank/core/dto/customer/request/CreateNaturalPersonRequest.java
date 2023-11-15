package com.equadis.bank.core.dto.customer.request;

import java.util.Objects;

public record CreateNaturalPersonRequest(
        String username,
        String email,
        String identificationNumber,
        String firstName,
        String lastName) {

    public CreateNaturalPersonRequest {
        Objects.requireNonNull(username);
        Objects.requireNonNull(identificationNumber);
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
    }


}
