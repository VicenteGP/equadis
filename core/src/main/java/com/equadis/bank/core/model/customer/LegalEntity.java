package com.equadis.bank.core.model.customer;

import com.equadis.bank.core.model.Card;
import com.equadis.bank.core.model.accounting.Account;
import com.equadis.bank.core.model.audit.Audit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name="legal_entity",
        indexes = @Index(name = "registration_number_index", columnList = "registration_number", unique = true))
public class LegalEntity extends Customer {

    @NotNull
    @Column(name="company_name", unique = true)
    private String companyName;

    @NotNull
    @Column(name="registration_number", unique = true)
    @Pattern(regexp = "^5\\d{8}$")
    private final String registrationNumber;

    @Builder
    public LegalEntity(long id, Audit audit, String username, String email, ArrayList<Account> accounts, Set<Card> cards, String companyName, String registrationNumber) {
        super(id, audit, CustomerType.LEGAL_ENTITY, username, email, accounts, cards);
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
    }
}
