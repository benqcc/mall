package com.mallall.vo;

import java.math.BigDecimal;

/**
 * Created by geely
 */
public class CartProductVo {

//结合了产品和购物车的一个抽象对象

    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;//购物车中此商品的数量
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
    private BigDecimal productTotalPrice;
    private Integer productStock;
    private Integer productChecked;//此商品是否勾选

    private String limitQuantity;//限制数量的一个返回结果

    public Integer getId() {
        return id;
    }

    public CartProductVo setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public CartProductVo setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getProductId() {
        return productId;
    }

    public CartProductVo setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CartProductVo setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public CartProductVo setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public CartProductVo setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
        return this;
    }

    public String getProductMainImage() {
        return productMainImage;
    }

    public CartProductVo setProductMainImage(String productMainImage) {
        this.productMainImage = productMainImage;
        return this;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public CartProductVo setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
        return this;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public CartProductVo setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
        return this;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public CartProductVo setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
        return this;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public CartProductVo setProductStock(Integer productStock) {
        this.productStock = productStock;
        return this;
    }

    public Integer getProductChecked() {
        return productChecked;
    }

    public CartProductVo setProductChecked(Integer productChecked) {
        this.productChecked = productChecked;
        return this;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public CartProductVo setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
        return this;
    }
}
