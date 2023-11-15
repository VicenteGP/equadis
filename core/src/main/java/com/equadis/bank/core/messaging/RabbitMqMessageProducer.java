package com.equadis.bank.core.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMqMessageProducer implements MessageProducer {

    private RabbitTemplate rabbitTemplate;

    public RabbitMqMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMessage(String routingKey, Object message) {
        log.info("Send message {} for {}.", message.getClass().getSimpleName(), routingKey);
        rabbitTemplate.convertAndSend(routingKey, message);
    }
}
