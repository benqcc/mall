package com.mallall.vo;

import java.math.BigDecimal;

/**
 * Created by geely
 */
public class ProductListVo {

    private Integer id;
    private Integer categoryId;

    private String name;
    private String subtitle;
    private String mainImage;
    private BigDecimal price;

    private Integer status;

    private String imageHost;

    public Integer getId() {
        return id;
    }

    public ProductListVo setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public ProductListVo setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductListVo setName(String name) {
        this.name = name;
        return this;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public ProductListVo setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public String getMainImage() {
        return mainImage;
    }

    public ProductListVo setMainImage(String mainImage) {
        this.mainImage = mainImage;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductListVo setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ProductListVo setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getImageHost() {
        return imageHost;
    }

    public ProductListVo setImageHost(String imageHost) {
        this.imageHost = imageHost;
        return this;
    }
}
