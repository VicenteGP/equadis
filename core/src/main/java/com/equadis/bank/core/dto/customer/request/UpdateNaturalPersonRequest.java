package com.equadis.bank.core.dto.customer.request;


public record UpdateNaturalPersonRequest(
        String username,
        String email,
        String firstName,
        String lastName) {
}
