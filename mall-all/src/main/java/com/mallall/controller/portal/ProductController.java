package com.mallall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mallall.common.Result;
import com.mallall.service.IProductService;
import com.mallall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: mall
 * @author: qcc
 * @description ProductController
 * @create: 2019-02-20 21:01
 * @Version: 1.0
 **/
@RestController
@RequestMapping(value = "/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @RequestMapping(value = "detail")
    @ResponseBody
    public Result<ProductDetailVo> detail(Integer productId){
        return iProductService.getPortalProductDetail(productId);
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public Result<PageInfo> findProductPage(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "orderBy", defaultValue = "") String orderBy
    ){
        return iProductService.findProductPage(keyword,categoryId,pageNum,pageSize,orderBy);
    }
}
