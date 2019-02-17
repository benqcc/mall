package com.mallall.dao;

import com.mallall.pojo.OrderItem;
import java.util.List;

public interface OrderItemMapper {
    int insert(OrderItem record);

    List<OrderItem> selectAll();
}