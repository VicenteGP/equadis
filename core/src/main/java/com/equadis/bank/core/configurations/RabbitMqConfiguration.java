package com.equadis.bank.core.configurations;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqConfiguration {

    private AmqpAdmin amqpAdmin;

    public RabbitMqConfiguration(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String name){
        return new Queue(name, true, false, false);
    }


    private Binding binding(Queue queue, DirectExchange exchange){
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void addQueues(){
        Queue coreQueue = queue(Constants.SCHEDULE_INTEREST_QUEUE);

        DirectExchange directExchange = new DirectExchange("amq.direct");

        Binding coreBinding = binding(coreQueue, directExchange);

        this.amqpAdmin.declareQueue(coreQueue);

        this.amqpAdmin.declareExchange(directExchange);

        this.amqpAdmin.declareBinding(coreBinding);
    }

}
