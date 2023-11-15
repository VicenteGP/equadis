package com.equadis.bank.core.messaging;

public interface MessageProducer {

    void sendMessage(String routingKey, Object message);
}
