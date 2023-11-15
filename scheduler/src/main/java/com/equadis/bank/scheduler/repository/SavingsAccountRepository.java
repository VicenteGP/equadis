package com.equadis.bank.scheduler.repository;

import com.equadis.bank.scheduler.model.SavingsAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SavingsAccountRepository extends CrudRepository<SavingsAccount, Long> {

    @Query(value = "select sa from savings_account sa where last_update <= DATE_SUB(NOW(),INTERVAL 1 YEAR)", nativeQuery = true)
    List<SavingsAccount> findSavingsAccountNotUpdatedInAYear();
}
