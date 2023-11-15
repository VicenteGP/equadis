package com.equadis.bank.core.dto.accounting;

public record CreateCheckingAccountRequest(String username, long initialBalance) {
}
