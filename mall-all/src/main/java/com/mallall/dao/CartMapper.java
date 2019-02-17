package com.mallall.dao;

import com.mallall.pojo.Cart;
import java.util.List;

public interface CartMapper {
    int insert(Cart record);

    List<Cart> selectAll();
}