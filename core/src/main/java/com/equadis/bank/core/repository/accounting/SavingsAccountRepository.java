package com.equadis.bank.core.repository.accounting;

import com.equadis.bank.core.model.accounting.SavingsAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsAccountRepository extends CrudRepository<SavingsAccount, Long> {
}
