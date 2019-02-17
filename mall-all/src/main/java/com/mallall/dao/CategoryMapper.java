package com.mallall.dao;

import com.mallall.pojo.Category;
import java.util.List;

public interface CategoryMapper {
    int insert(Category record);

    List<Category> selectAll();
}