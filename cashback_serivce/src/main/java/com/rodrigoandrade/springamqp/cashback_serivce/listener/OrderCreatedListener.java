package com.rodrigoandrade.springamqp.cashback_serivce.listener;

import com.rodrigoandrade.springamqp.cashback_serivce.event.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderCreatedListener {

    @RabbitListener(queues = "orders.v1.order-created.generate-cashback")
    public void onOrderCreated(OrderCreatedEvent event) {
        System.out.println("Id recebido " + event.getId());
        System.out.println("Valor recebido " + event.getValue());

        if(event.getValue().compareTo(new BigDecimal("10000")) >= 0) {
            throw new RuntimeException("Falha no processamento da venda de id"+event.getId());
        }
    }
}
