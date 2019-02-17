package com.mallall.dao;

import com.mallall.pojo.User;
import java.util.List;

public interface UserMapper {
    int insert(User record);

    List<User> selectAll();
}