package com.mallall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mallall.common.Const;
import com.mallall.common.Result;
import com.mallall.common.StatusCode;
import com.mallall.pojo.Shipping;
import com.mallall.pojo.User;
import com.mallall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @program: mall
 * @author: qcc
 * @description ShippingController
 * @create: 2019-02-22 12:12
 * @Version: 1.0
 **/
@RestController
@RequestMapping(value = "/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;

    @RequestMapping(value = "add")
    public Result add(HttpSession session, Shipping shipping) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), StatusCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.add(user.getId(),shipping);
    }

    @RequestMapping(value = "delete")
    public Result delete(HttpSession session, Integer shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), StatusCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.delete(user.getId(),shippingId);
    }

    @RequestMapping(value = "add")
    public Result edit(HttpSession session, Shipping shipping) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), StatusCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.edit(user.getId(),shipping);
    }

    @RequestMapping(value = "select")
    public Result<Shipping> select(HttpSession session, Integer shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), StatusCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.select(user.getId(),shippingId);
    }

    @RequestMapping(value = "list")
    public Result<PageInfo> findShippingPage(
            @RequestParam( value = "pageNum",defaultValue = "1") int pageNum,
            @RequestParam( value = "pageSize", defaultValue = "10") int pageSize,
            HttpSession session
    ){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), StatusCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.findShippingPage(user.getId(),pageNum,pageSize);
    }
}
