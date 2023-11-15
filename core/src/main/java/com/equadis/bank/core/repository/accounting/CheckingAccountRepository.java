package com.equadis.bank.core.repository.accounting;

import com.equadis.bank.core.model.accounting.CheckingAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingAccountRepository extends CrudRepository<CheckingAccount, Long> {
}
