package com.mallall.controller.portal;

import com.mallall.common.Const;
import com.mallall.common.Result;
import com.mallall.common.StatusCode;
import com.mallall.pojo.User;
import com.mallall.service.ICartService;
import com.mallall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @program: mall
 * @author: qcc
 * @description CartController
 * @create: 2019-02-21 09:40
 * @Version: 1.0
 **/
@RestController
@RequestMapping(value = "/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @RequestMapping(value = "add")
    @ResponseBody
    public Result<CartVo> add(
            HttpSession session,
            Integer count,
            Integer productId
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), StatusCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(user.getId(), count, productId);
    }


    @RequestMapping(value = "edit")
    @ResponseBody
    public Result<CartVo> edit(
            HttpSession session,
            Integer count,
            Integer productId
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), StatusCode.NEED_LOGIN.getDesc());
        }
        return iCartService.edit(user.getId(), count, productId);
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public Result<CartVo> delete(
            HttpSession session,
            String productIds
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), StatusCode.NEED_LOGIN.getDesc());
        }
        return iCartService.delete(user.getId(), productIds);
    }

    @RequestMapping(value = "find")
    @ResponseBody
    public Result<CartVo> findCartList(
            HttpSession session
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), StatusCode.NEED_LOGIN.getDesc());
        }
        return iCartService.findCartList(user.getId());
    }


    @RequestMapping(value = "selectAll")
    @ResponseBody
    public Result<CartVo> selectAll(
            HttpSession session
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), StatusCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.CHECKED);
    }

    @RequestMapping(value = "unSelectAll")
    @ResponseBody
    public Result<CartVo> unSelectAll(
            HttpSession session
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), StatusCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.UN_CHECKED);
    }

    @RequestMapping(value = "unSelect")
    @ResponseBody
    public Result<CartVo> unSelect(
            HttpSession session,
            Integer productId
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), StatusCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.UN_CHECKED);
    }

    @RequestMapping(value = "select")
    @ResponseBody
    public Result<CartVo> select(
            HttpSession session,
            Integer productId
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), StatusCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.CHECKED);
    }

    @RequestMapping(value = "getCartProductCount")
    @ResponseBody
    public Result<Integer> getCartProductCount(
            HttpSession session
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createBySuccess(0);
        }
        return iCartService.getCartProductCount(user.getId());
    }
}
