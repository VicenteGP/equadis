package com.equadis.bank.core.utils.mappers;

import com.equadis.bank.core.dto.transaction.TransactionSearchResponse;
import com.equadis.bank.core.model.transaction.Transaction;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionMapper {

    public static TransactionSearchResponse toTransactionSearchResponse(Transaction transaction) {
        return TransactionSearchResponse.builder()
                .timestamp(transaction.getTimestamp())
                .type(transaction.getType())
                .category(transaction.getCategory())
                .amount(transaction.getAmount())
                .currentBalance(transaction.getCurrentBalance())
                .description(transaction.getDescription())
                .build();
    }
}
