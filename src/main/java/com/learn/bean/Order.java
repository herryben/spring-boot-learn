package com.learn.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
@Data(staticConstructor = "newOrder")
public class Order {
    private long id;
    private String name;
    private int count;
    private int num;
    private BigDecimal total;
}
