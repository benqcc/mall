package com.mallall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mallall.common.Result;
import com.mallall.dao.ShippingMapper;
import com.mallall.pojo.Shipping;
import com.mallall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: mall
 * @author: qcc
 * @description ShippingServiceImpl
 * @create: 2019-02-22 12:20
 * @Version: 1.0
 **/
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public Result add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0) {
            Map<String,Object> result = Maps.newHashMap();
            result.put("shippingId",shipping.getId());
            return Result.createBySuccess("新建地址成功",result);
        }
        return Result.createByErrorMessage("新建地址失败");
    }

    @Override
    public Result delete(Integer userId,Integer shippingId) {
        int resultCount = shippingMapper.deleteByShippingIdUserId(userId, shippingId);
        if(resultCount >0){
            return Result.createBySuccess("删除地址成功");
        }
        return Result.createByErrorMessage("删除地址失败");
    }

    @Override
    public Result edit(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByPrimaryKeySelective(shipping);
        if(rowCount >0){
            return Result.createBySuccess("修改地址成功");
        }
        return Result.createByErrorMessage("修改地址失败");
    }

    @Override
    public Result<Shipping> select(Integer userId, Integer shippingId) {
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId,shippingId);
        if(shipping == null){
            return Result.createByErrorMessage("无法查询到该地址");
        }
        return Result.createBySuccess("更新地址成功",shipping);
    }

    @Override
    public Result<PageInfo> findShippingPage(int pageNum, int pageSize, Integer userId) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return Result.createBySuccess(pageInfo);
    }
}
