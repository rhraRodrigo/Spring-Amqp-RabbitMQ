package com.rodrigoandrade.springamqp.order_service.controller;

import com.rodrigoandrade.springamqp.order_service.entity.OrderEntity;
import com.rodrigoandrade.springamqp.order_service.event.OrderCreatedEvent;
import com.rodrigoandrade.springamqp.order_service.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    public OrderEntity create(@RequestBody OrderEntity order){
        orderRepository.save(order);

        OrderCreatedEvent event  = new OrderCreatedEvent(order.getId(), order.getValue());
        rabbitTemplate.convertAndSend("orders.v1.order-created", "", event);

        return order;

    }

    @GetMapping
    public List<OrderEntity> list(){
        return orderRepository.findAll();
    }

    @GetMapping("{id}")
    public OrderEntity findById(@PathVariable Long id){
        return orderRepository.findById(id).orElseThrow();
    }

    @PutMapping("{id}/pay")
    public OrderEntity pay(@PathVariable Long id){
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow();
        orderEntity.markAsPaid();
        return orderRepository.save(orderEntity);
    }
}
