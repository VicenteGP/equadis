package com.equadis.bank.core.dto.transaction;

import com.equadis.bank.core.model.transaction.TransactionCategory;
import com.equadis.bank.core.model.transaction.TransactionType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

public record TransactionSearchFilter(
        TransactionCategory category,
        TransactionType type,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to ) {
}
