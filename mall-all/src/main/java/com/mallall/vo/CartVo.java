package com.mallall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by geely
 */
public class CartVo {

    private List<CartProductVo> cartProductVoList;
    private BigDecimal cartTotalPrice;
    private Boolean allChecked;//是否已经都勾选
    private String imageHost;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public CartVo setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
        return this;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public CartVo setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
        return this;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public CartVo setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
        return this;
    }

    public String getImageHost() {
        return imageHost;
    }

    public CartVo setImageHost(String imageHost) {
        this.imageHost = imageHost;
        return this;
    }
}
