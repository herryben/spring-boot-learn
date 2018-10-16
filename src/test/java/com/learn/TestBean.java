package com.learn;

import com.learn.bean.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
@Slf4j
public class TestBean {
    @Test
    public void testOrder() {
        Order order = Order.newOrder();
        order.setCount(4);
        log.info("this is order {}", order);
        System.out.println(order.getId());
    }
}
