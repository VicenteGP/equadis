package com.equadis.bank.scheduler.broker.consumer;

import com.equadis.bank.scheduler.dto.AccountInterestMessage;

public interface MessageConsumer {

    void savingsAccountInterest(AccountInterestMessage AccountInterestMessage);
}
