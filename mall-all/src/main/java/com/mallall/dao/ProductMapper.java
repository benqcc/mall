package com.mallall.dao;

import com.mallall.pojo.Product;
import java.util.List;

public interface ProductMapper {
    int insert(Product record);

    List<Product> selectAll();
}