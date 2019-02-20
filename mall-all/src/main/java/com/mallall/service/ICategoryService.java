package com.mallall.service;

import com.mallall.common.Result;
import com.mallall.pojo.Category;

import java.util.List;

/**
 * @program: mall
 * @author: qcc
 * @description ICategoryService
 * @create: 2019-02-19 17:46
 * @Version: 1.0
 **/
public interface ICategoryService {

    Result addCategory(String categoryName,Integer parentId);

    Result editCategory(Integer categoryId,String categoryName);

    Result<List<Category>> getChildrenParallelCategory(Integer categoryId);

    Result selectCategoryAndChildrenById(Integer categoryId);
}
