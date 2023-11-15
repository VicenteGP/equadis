package com.equadis.bank.core.model.accounting;

import com.equadis.bank.core.model.audit.Audit;
import com.equadis.bank.core.model.transaction.Transaction;
import com.equadis.bank.core.model.customer.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.Set;
import java.util.UUID;


@Data
@Entity
@Table(name="savings_account")
public class SavingsAccount extends Account {

    @Column(name = "interest")
    @Size(max = 1)
    private float interest;

    @Column(name = "withdrawalEnable")
    private boolean withdrawalEnable;

    @Builder
    public SavingsAccount(long id, Audit audit, UUID identificationNumber, AccountType type, double balance, Set<Customer> customers, Set<Transaction> transactions, float interest, boolean withdrawalEnable) {
        super(id, audit, identificationNumber, type, balance, customers, transactions);
        this.interest = interest;
        this.withdrawalEnable = withdrawalEnable;
    }
}
