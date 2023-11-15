package com.equadis.bank.core.dto.accounting;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record ApplyInterestRequest(@NotNull Set<String> identificationNumbers) {
}
