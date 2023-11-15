package com.equadis.bank.scheduler.service;

import com.equadis.bank.scheduler.dto.AccountInterestMessage;
import com.equadis.bank.scheduler.model.SavingsAccount;
import com.equadis.bank.scheduler.repository.SavingsAccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageConsumerService {

    private final SavingsAccountRepository savingsAccountRepository;

    public MessageConsumerService(SavingsAccountRepository savingsAccountRepository) {
        this.savingsAccountRepository = savingsAccountRepository;
    }

    public SavingsAccount saveSavingsAccount(AccountInterestMessage message) {
        SavingsAccount savingsAccount = SavingsAccount.builder()
                .identificationNumber(UUID.fromString(message.identificationNumber()))
                .lastUpdate(message.createdAt())
                .build();
        return savingsAccountRepository.save(savingsAccount);
    }
}
