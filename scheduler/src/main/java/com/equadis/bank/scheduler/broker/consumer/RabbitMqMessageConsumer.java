package com.equadis.bank.scheduler.broker.consumer;

import com.equadis.bank.scheduler.configuration.Constants;
import com.equadis.bank.scheduler.dto.AccountInterestMessage;
import com.equadis.bank.scheduler.service.MessageConsumerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqMessageConsumer implements MessageConsumer {

    private MessageConsumerService messageConsumerService;

    public RabbitMqMessageConsumer(MessageConsumerService messageConsumerService) {
        this.messageConsumerService = messageConsumerService;
    }

    @RabbitListener(queues = Constants.SCHEDULE_INTEREST_QUEUE)
    @Override
    public void savingsAccountInterest(AccountInterestMessage AccountInterestMessage) {
        messageConsumerService.saveSavingsAccount(AccountInterestMessage);
    }
}
