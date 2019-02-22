package com.mallall.pojo;

import java.util.Date;

public class Cart {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private Integer checked;

    private Date createTime;

    private Date updateTime;

    public Cart(Integer id, Integer userId, Integer productId, Integer quantity, Integer checked, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.checked = checked;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Cart() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public Cart setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Cart setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getProductId() {
        return productId;
    }

    public Cart setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Cart setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Integer getChecked() {
        return checked;
    }

    public Cart setChecked(Integer checked) {
        this.checked = checked;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Cart setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Cart setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}