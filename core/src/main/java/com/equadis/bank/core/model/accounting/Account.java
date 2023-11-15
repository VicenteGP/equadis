package com.equadis.bank.core.model.accounting;

import com.equadis.bank.core.model.audit.Audit;
import com.equadis.bank.core.model.audit.Auditable;
import com.equadis.bank.core.model.transaction.Transaction;
import com.equadis.bank.core.model.customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name="account",
        indexes = @Index(name = "identification_number_index", columnList = "identification_number", unique = true))
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_id")
    private Audit audit;

    @NotNull
    @Column(name="identification_number")
    private final UUID identificationNumber;

    @NotNull
    @Column(name="type")
    private final AccountType type;

    @Min(0)
    @Column(name="balance")
    private double balance;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "account_customer",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> customers;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private Set<Transaction> transactions;

    public Account(long id, Audit audit, UUID identificationNumber, AccountType type, double balance, Set<Customer> customers, Set<Transaction> transactions) {
        this.id = id;
        this.audit = audit;
        this.identificationNumber = identificationNumber;
        this.type = type;
        this.balance = balance;
        this.customers = customers;
        this.transactions = transactions;
    }

    public boolean isSavingsAccount() {
        return AccountType.SAVINGS.equals(this.type);
    }

    public boolean isCheckingAccount() {
        return AccountType.CHECKING.equals(this.type);
    }
}
