package com.equadis.bank.core.model.transaction;

import com.equadis.bank.core.model.accounting.Account;
import com.equadis.bank.core.model.customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.UUID;

@Value
@Builder
@Entity
@Table(name="transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    int id;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="account_identification_number", referencedColumnName = "identification_number")
    Account account;

    @Column(name = "account_identification_number", insertable = false, updatable = false)
    UUID identificationNumber;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="customer_username", referencedColumnName = "username")
    Customer customer;

    @Column(name = "customer_username", insertable = false, updatable = false)
    String username;

    @NotNull
    @Column(name="timestamp")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    OffsetDateTime timestamp;

    @NotNull
    @Column(name="category")
    TransactionCategory category;

    @NotNull
    @Column(name="type")
    TransactionType type;

    @Min(1)
    @Column(name="amount")
    double amount;

    @Min(1)
    @Column(name="current_balance")
    double currentBalance;

    @Column(name="description")
    String description;

}
