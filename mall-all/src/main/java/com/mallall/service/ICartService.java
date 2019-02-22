package com.mallall.service;

import com.mallall.common.Result;
import com.mallall.vo.CartVo;

/**
 * Created by qcc on 2/21/2019.
 */
public interface ICartService {

    Result<CartVo> add(Integer userId, Integer count, Integer productId);

    Result<CartVo> edit(Integer userId, Integer count, Integer productId);

    Result<CartVo> delete(Integer userId,String productIds);

    Result<CartVo> findCartList(Integer userId);

    Result<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    Result<Integer> getCartProductCount(Integer userId);
}
