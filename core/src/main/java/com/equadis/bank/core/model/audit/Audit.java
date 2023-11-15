package com.equadis.bank.core.model.audit;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name="audit")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @NotNull
    @Column(name="createdAt")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final OffsetDateTime createdAt;

    @Column(name="updateAd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime updateAd;

    @Builder(toBuilder = true)
    public Audit(long id, OffsetDateTime createdAt, OffsetDateTime updateAd) {
        this.id = id;
        this.createdAt = createdAt;
        this.updateAd = updateAd;
    }
}
