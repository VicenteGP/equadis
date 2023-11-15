package com.equadis.bank.core.dto.messages;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public record AccountInterestMessage(String identificationNumber, LocalDate createdAt) implements Serializable {
    @Builder public AccountInterestMessage {

    }
}
