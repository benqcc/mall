package com.mallall.dao;

import com.mallall.pojo.Shipping;
import java.util.List;

public interface ShippingMapper {
    int insert(Shipping record);

    List<Shipping> selectAll();
}