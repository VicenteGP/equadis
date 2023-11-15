package com.equadis.bank.core.model.accounting;

import com.equadis.bank.core.model.Card;
import com.equadis.bank.core.model.audit.Audit;
import com.equadis.bank.core.model.transaction.Transaction;
import com.equadis.bank.core.model.customer.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name="checking_account")
public class CheckingAccount extends Account {

    @OneToMany(mappedBy = "account")
    private Set<Card> cards;


    @Builder
    public CheckingAccount(long id, Audit audit, UUID identificationNumber, AccountType type, double balance, Set<Customer> customers, Set<Transaction> transactions) {
        super(id, audit, identificationNumber, type, balance, customers, transactions);
    }
}
