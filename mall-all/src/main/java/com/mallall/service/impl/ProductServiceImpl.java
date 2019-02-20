package com.mallall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mallall.common.Const;
import com.mallall.common.Result;
import com.mallall.common.StatusCode;
import com.mallall.dao.CategoryMapper;
import com.mallall.dao.ProductMapper;
import com.mallall.pojo.Category;
import com.mallall.pojo.Product;
import com.mallall.service.ICategoryService;
import com.mallall.service.IProductService;
import com.mallall.util.DateTimeUtil;
import com.mallall.util.PropertiesUtil;
import com.mallall.vo.ProductDetailVo;
import com.mallall.vo.ProductListVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: mall
 * @author: qcc
 * @description ProductServiceImpl
 * @create: 2019-02-20 09:59
 * @Version: 1.0
 **/
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

    @Override
    public Result saveOrUpdateProduct(Product product) {
        if (product != null) {
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }

            if (product.getId() != null) {
                int rowCount = productMapper.updateByPrimaryKey(product);
                if (rowCount > 0) {
                    return Result.createBySuccess("修改产品成功成功");
                }
                return Result.createByErrorMessage("修改产品失败");
            } else {
                int rowCount = productMapper.insert(product);
                if (rowCount > 0) {
                    return Result.createBySuccess("新增产品成功");
                }
                return Result.createByErrorMessage("新增产品失败");
            }
        }
        return Result.createByErrorMessage("新增或更新产品参数不正确");
    }

    @Override
    public Result setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return Result.createByErrorCodeMessage(StatusCode.ILLEGAL_ARGUMENT.getCode(), StatusCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0) {
            return Result.createBySuccess("修改产品销售状态成功");
        }
        return Result.createByErrorMessage("修改产品销售状态失败");
    }

    @Override
    public Result<ProductDetailVo> getProductDetail(Integer productId) {
        if (productId != null) {
            Product product = productMapper.selectByPrimaryKey(productId);
            if (product != null) {
                ProductDetailVo productDetailVo = assembleProductDetailVo(product);
                return Result.createBySuccess(productDetailVo);
            }
        }
        return Result.createByErrorMessage("产品不存在,或者已经下架");
    }

    private ProductDetailVo assembleProductDetailVo(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());


        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "www.test.com"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());

        if (category == null) {
            //假如这个对象是空的 那么就认为它是根节点
            productDetailVo.setParentCategoryId(0);
        } else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));


        return productDetailVo;
    }

    @Override
    public Result<PageInfo> getProductList(int pageNum, int pageSize) {
        //startPage 填充自己的sql查询收尾
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectList();

        return getPageInfoResult(productList);
    }

    private ProductListVo assembleProductList(Product product) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId())
                .setName(product.getName())
                .setCategoryId(product.getCategoryId())
                .setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "www.test.com"))
                .setMainImage(product.getMainImage())
                .setPrice(product.getPrice())
                .setSubtitle(product.getSubtitle())
                .setStatus(product.getStatus());
        return productListVo;
    }

    @Override
    public Result<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotBlank(productName)) {
            productName = "%" + productName + "%";
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName, productId);
        return getPageInfoResult(productList);
    }

    private Result<PageInfo> getPageInfoResult(List<Product> productList) {
        PageInfo pageInfo = getPageInfo(productList);
        return Result.createBySuccess(pageInfo);
    }

    @Override
    public Result<ProductDetailVo> getPortalProductDetail(Integer productId) {
        if (productId == null) {
            return Result.createByErrorCodeMessage(StatusCode.ILLEGAL_ARGUMENT.getCode(), StatusCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return Result.createByErrorMessage("产品不存在,或者已经下架");
        }
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
            return Result.createByErrorMessage("产品不存在,或者已经下架");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return Result.createBySuccess(productDetailVo);
    }

    @Override
    public Result<PageInfo> findProductPage(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy) {
        if (StringUtils.isBlank(keyword) && categoryId == null) {
            return Result.createByErrorCodeMessage(StatusCode.ILLEGAL_ARGUMENT.getCode(), StatusCode.ILLEGAL_ARGUMENT.getDesc());
        }

        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isBlank(keyword)) {
                //没有找到该分类,关键字为空,这个适合返回一个空的结果集
                PageHelper.startPage(pageNum, pageSize);
                List<ProductDetailVo> productDetailVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productDetailVoList);
                return Result.createBySuccess(pageInfo);
            }
            categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();
        }
        if (StringUtils.isNotBlank(keyword)) {
            keyword = "%" + keyword + "%";
        }

        //排序处理
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotBlank(orderBy)) {
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
            }
        }
        List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword) ? null : keyword, categoryIdList.isEmpty() ? null : categoryIdList);

        PageInfo pageInfo = getPageInfo(productList);
        return Result.createBySuccess(pageInfo);
    }

    private PageInfo getPageInfo(List<Product> productList) {
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product product : productList) {
            ProductListVo productListVo = assembleProductList(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return pageInfo;
    }
}
