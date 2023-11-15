package com.equadis.bank.core.exceptions.errors;

public enum CustomerErrorCode implements ErrorCode {

    CUSTOMER_NOT_FOUND("C0001", "The customer %s not found.");

    private final String code;

    private final String description;

    CustomerErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
