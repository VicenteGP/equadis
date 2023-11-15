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
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="natural_person",
        indexes = @Index(name = "identification_number_index", columnList = "identification_number", unique = true))
public class NaturalPerson extends Customer {

    @NotNull
    @Column(name="identification_number", unique = true)
    @Pattern(regexp = "^[1-3]\\d{8}$")
    private final String identificationNumber;

    @NotNull
    @Column(name="first_name")
    private String firstName;

    @NotNull
    @Column(name="last_name")
    private String lastName;

    @Builder
    public NaturalPerson(long id, Audit audit, String username, String email, ArrayList<Account> accounts, Set<Card> cards, String identificationNumber, String firstName, String lastName) {
        super(id, audit, CustomerType.NATURAL_PERSON, username, email, accounts, cards);
        this.identificationNumber = identificationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }


}
