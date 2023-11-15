package com.equadis.bank.scheduler.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity(name = "savings_account")
public class SavingsAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    long id;

    @NotNull
    @Column(name="identification_number")
    UUID identificationNumber;

    @NotNull
    @Column(name="last_update")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate lastUpdate;

    @Builder
    public SavingsAccount(long id, UUID identificationNumber, LocalDate lastUpdate) {
        this.id = id;
        this.identificationNumber = identificationNumber;
        this.lastUpdate = lastUpdate;
    }
}
