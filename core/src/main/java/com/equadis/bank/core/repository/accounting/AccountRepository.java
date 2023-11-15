package com.equadis.bank.core.repository.accounting;

import com.equadis.bank.core.model.accounting.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByIdentificationNumber(UUID identificationNumber);

    @Query("SELECT a FROM Account a WHERE a.identificationNumber IN :identificationNumbers")
    List<Account> findBySetIdentificationNumbers(@Param("identificationNumbers") Set<String> identificationNumbers);

}
