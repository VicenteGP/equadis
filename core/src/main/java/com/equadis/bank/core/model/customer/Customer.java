package com.equadis.bank.core.model.customer;

import com.equadis.bank.core.model.Card;
import com.equadis.bank.core.model.accounting.Account;
import com.equadis.bank.core.model.audit.Audit;
import com.equadis.bank.core.model.audit.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.ArrayList;
import java.util.Set;

@Data
@Entity
@Table(name = "customer",
        indexes = @Index(name = "username_index", columnList = "username", unique = true))
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Customer implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_id")
    private Audit audit;

    @NotNull
    @Column(name="type")
    private CustomerType type;

    @NotNull
    @Column(name="username", unique = true)
    private String username;

    @Column(name="email", unique = true)
    @Pattern(regexp = ".*@.*")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "account_customer",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private ArrayList<Account> accounts;

    @OneToMany(mappedBy = "customer")
    private Set<Card> cards;

    public Customer(long id, Audit audit, CustomerType type, String username, String email, ArrayList<Account> accounts, Set<Card> cards) {
        this.id = id;
        this.audit = audit;
        this.type = type;
        this.username = username;
        this.email = email;
        this.accounts = accounts;
        this.cards = cards;
    }

    public boolean isLegalEntity() {
        return CustomerType.LEGAL_ENTITY.equals(this.type);
    }

    public boolean isNaturalPerson() {
        return CustomerType.NATURAL_PERSON.equals(this.type);
    }
}
