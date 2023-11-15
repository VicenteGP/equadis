package com.equadis.bank.core.repository.transaction;


import com.equadis.bank.core.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long>, TransactionsRepositoryCustom {

}
