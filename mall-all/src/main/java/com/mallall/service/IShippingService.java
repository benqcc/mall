package com.mallall.service;

import com.github.pagehelper.PageInfo;
import com.mallall.common.Result;
import com.mallall.pojo.Shipping;

/**
 * @program: mall
 * @author: qcc
 * @description IShippingService
 * @create: 2019-02-22 12:19
 * @Version: 1.0
 **/
public interface IShippingService {

    Result add(Integer userId, Shipping shipping);

    Result delete(Integer userId,Integer shippingId);

    Result edit(Integer userId,Shipping shipping);

    Result<Shipping> select(Integer userId,Integer shippingId);

    Result<PageInfo> findShippingPage(int pageNum,int pageSize,Integer userId);
}
