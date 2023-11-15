package com.equadis.bank.core.repository.customer;

import com.equadis.bank.core.model.customer.Customer;
import com.equadis.bank.core.model.customer.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByUsername(String username);

    @Query("SELECT c FROM Customer c WHERE c.username = :username AND c.type = \"LEGAL_ENTITY\"")
    Customer findLegalEntityByUsername(@Param("username") String username);

    @Query("SELECT c FROM Customer c WHERE c.username = :username AND c.type = \"NATURAL_PERSON\"")
    Customer findNaturalPersonByUsername(@Param("username") String username);
}
