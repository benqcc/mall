package com.mallall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mallall.common.Const;
import com.mallall.common.Result;
import com.mallall.common.StatusCode;
import com.mallall.dao.CartMapper;
import com.mallall.dao.ProductMapper;
import com.mallall.pojo.Cart;
import com.mallall.pojo.Product;
import com.mallall.service.ICartService;
import com.mallall.util.BigDecimalUtil;
import com.mallall.vo.CartProductVo;
import com.mallall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: mall
 * @author: qcc
 * @description ICartServiceImpl
 * @create: 2019-02-21 09:44
 * @Version: 1.0
 **/
@Service
public class ICartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Result<CartVo> add(Integer userId, Integer count, Integer productId) {
        if(count == null || productId == null){
            return Result.createByErrorCodeMessage(StatusCode.ILLEGAL_ARGUMENT.getCode(),StatusCode.NEED_LOGIN.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart == null) {
            Cart cartItem = new Cart();
            cartItem.setChecked(Const.Cart.CHECKED)
                    .setProductId(productId)
                    .setUserId(userId)
                    .setQuantity(count);
            cartMapper.insert(cartItem);
        } else {
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.findCartList(userId);
    }

    @Override
    public Result<CartVo> edit(Integer userId, Integer count, Integer productId) {
        if(count == null || productId == null){
            return Result.createByErrorCodeMessage(StatusCode.ILLEGAL_ARGUMENT.getCode(),StatusCode.NEED_LOGIN.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        if(cart != null){
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKeySelective(cart);
        return this.findCartList(userId);
    }

    @Override
    public Result<CartVo> delete(Integer userId,String productIds) {
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if(CollectionUtils.isNotEmpty(productList)){
            return Result.createByErrorCodeMessage(StatusCode.ILLEGAL_ARGUMENT.getCode(),StatusCode.NEED_LOGIN.getDesc());
        }
        cartMapper.deleteByUserIdProductIds(userId,productList);
        return this.findCartList(userId);
    }

    @Override
    public Result<CartVo> findCartList(Integer userId) {
        CartVo cartVo = this.getCartVoLimit(userId);
        return Result.createBySuccess(cartVo);
    }

    @Override
    public Result<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked) {
        cartMapper.checkedOrUncheckedProduct(userId,productId,checked);
        return this.findCartList(userId);
    }

    @Override
    public Result<Integer> getCartProductCount(Integer userId) {
        if(userId == null){
            return Result.createBySuccess(0);
        }
        int productCount = cartMapper.selectCartProductCount(userId);
        return Result.createBySuccess(productCount);
    }

    private CartVo getCartVoLimit(Integer userId) {
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        if (CollectionUtils.isNotEmpty(cartList)) {
            for (Cart cartItem : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId())
                        .setUserId(cartItem.getUserId())
                        .setProductId(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if(product != null){
                    cartProductVo.setProductMainImage(product.getMainImage())
                            .setProductName(product.getName())
                            .setProductSubtitle(product.getSubtitle())
                            .setProductStatus(product.getStatus())
                            .setProductPrice(product.getPrice())
                            .setProductStock(product.getStock());

                    //判断库存
                    int buyLimitCount=0;
                    if(product.getStock() >= cartItem.getQuantity()){
                        //库存充足时
                        buyLimitCount=product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else{
                        buyLimitCount= product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId())
                                .setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }

                if(cartItem.getChecked() == Const.Cart.CHECKED){
                    //如果已经勾选,增加到整个购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                    cartProductVoList.add(cartProductVo);
                }
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice)
                .setCartProductVoList(cartProductVoList)
                .setAllChecked(this.getAllCheckedStatus(userId))
                .setImageHost("ftp.server.http.prefix");
        return cartVo;

    }

    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId)==0;
    }
}
