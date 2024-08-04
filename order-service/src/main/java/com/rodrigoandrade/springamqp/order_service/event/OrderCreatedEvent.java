package com.rodrigoandrade.springamqp.order_service.event;

import java.math.BigDecimal;

public record OrderCreatedEvent(Long id, BigDecimal value) {

}
