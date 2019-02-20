package com.mallall.service;

import com.github.pagehelper.PageInfo;
import com.mallall.common.Result;
import com.mallall.pojo.Product;
import com.mallall.vo.ProductDetailVo;

/**
 * @program: mall
 * @author: qcc
 * @description IProductService
 * @create: 2019-02-20 09:58
 * @Version: 1.0
 **/
public interface IProductService {

    Result saveOrUpdateProduct(Product product);

    Result setSaleStatus(Integer productId,Integer status);

    Result<ProductDetailVo> getProductDetail(Integer productId);

    Result<PageInfo> getProductList(int pageNum, int pageSize);

    Result<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize);

    Result<ProductDetailVo> getPortalProductDetail(Integer productId);

    Result<PageInfo> findProductPage(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy);
}
