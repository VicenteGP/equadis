package com.equadis.bank.core.utils.mappers;

import com.equadis.bank.core.dto.messages.AccountInterestMessage;
import com.equadis.bank.core.model.accounting.CheckingAccount;
import com.equadis.bank.core.model.accounting.SavingsAccount;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageMapper {

    public static AccountInterestMessage toAccountInterestMessage(SavingsAccount savingsAccount) {
        return AccountInterestMessage.builder()
                .identificationNumber(savingsAccount.getIdentificationNumber().toString())
                .createdAt(savingsAccount.getAudit().getCreatedAt().toLocalDate())
                .build();
    }
}
