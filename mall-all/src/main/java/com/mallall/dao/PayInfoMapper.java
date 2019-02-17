package com.mallall.dao;

import com.mallall.pojo.PayInfo;
import java.util.List;

public interface PayInfoMapper {
    int insert(PayInfo record);

    List<PayInfo> selectAll();
}