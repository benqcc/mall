package com.mallall.dao;

import com.mallall.pojo.Order;
import java.util.List;

public interface OrderMapper {
    int insert(Order record);

    List<Order> selectAll();
}