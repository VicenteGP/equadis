package com.equadis.bank.core.exceptions.errors;

public enum AccountingErrorCode implements ErrorCode {

    ACCOUNT_NOT_FOUND("A0001", "The account %s not found."),
    CUSTOMER_ALREADY_ADDED("A0002", "The customer %s is already associated the account.");

    private final String code;

    private final String description;

    AccountingErrorCode(String code, String description) {
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
