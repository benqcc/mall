package com.mallall.controller.portal;

import com.mallall.common.Const;
import com.mallall.common.Result;
import com.mallall.pojo.User;
import com.mallall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @program: mall
 * @author: qcc
 * @description UserController
 * @create: 2019-02-17 19:37
 * @Version: 1.0
 **/
@RestController
@RequestMapping(value = "/user/")
public class UserController {

    @Autowired
    private UserService userService;

    /***
     * 登录
     * @param user
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public Result login(User user, HttpSession session) {
        Result<User> userResult = userService.login(user);
        if (userResult.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER,userResult.getData());
        }
        return userResult;
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return Result.createBySuccess();
    }

    @RequestMapping(value = "register.do", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> register(User user){
        return userService.register(user);
    }

    @RequestMapping(value = "check_valid.do", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> checkValid(String str,String type){
        return userService.checkValid(str,type);
    }
}
