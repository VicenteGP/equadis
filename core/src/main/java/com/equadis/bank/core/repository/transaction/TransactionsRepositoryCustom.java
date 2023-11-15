package com.equadis.bank.core.repository.transaction;

import com.equadis.bank.core.dto.transaction.TransactionSearchFilter;
import com.equadis.bank.core.dto.transaction.TransactionSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionsRepositoryCustom {

    Page<TransactionSearchResponse> findBySearchCriteria(TransactionSearchFilter filter, Pageable pageable);
}
