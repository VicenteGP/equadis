package com.equadis.bank.core.exceptions.errors;

public enum TransactionErrorCode implements ErrorCode {

    TYPE_AMOUNT_CONFLICT("T0001", "The transaction of type %s has the amount %s"),
    WITHDRAWAL_DISABLE_CONFLICT("T0002", "The savings account %s is not able to withdraw money.");

    private final String code;

    private final String description;

    TransactionErrorCode(String code, String description) {
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
