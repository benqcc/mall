package com.mallall.controller.backent;

import com.mallall.common.Const;
import com.mallall.common.Result;
import com.mallall.common.StatusCode;
import com.mallall.pojo.User;
import com.mallall.service.ICategoryService;
import com.mallall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @program: mall
 * @author: qcc
 * @description CategoryManageController
 * @create: 2019-02-19 17:13
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/manager/category/")
public class CategoryManageController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService iCategoryService;


    @RequestMapping(value = "add_category")
    @ResponseBody
    public Result addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        //检查是否是管理员
        if(userService.checkAdminRole(user).isSuccess()){
            //是管理员 增加处理分类的业务逻辑
            return iCategoryService.addCategory(categoryName,parentId);
        }else{
            return Result.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "set_category_name")
    @ResponseBody
    public Result setCategoryName(HttpSession session,Integer categoryId,String categoryName){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        //检查是否是管理员
        if(userService.checkAdminRole(user).isSuccess()){
            //更新categoryName
            return iCategoryService.editCategory(categoryId,categoryName);

        }else{
            return Result.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "get_children_category")
    @ResponseBody
    public Result getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        //检查是否是管理员
        if(userService.checkAdminRole(user).isSuccess()){
            //查询子节点的category信息,并且不递归,保持平级
           return iCategoryService.getChildrenParallelCategory(categoryId);

        }else{
            return Result.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "get_children_deep_category")
    @ResponseBody
    public Result getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        //检查是否是管理员
        if(userService.checkAdminRole(user).isSuccess()){
            //查询当前节点的id和递归子节点的id
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        }else{
            return Result.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }
}
