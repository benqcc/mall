package com.mallall.controller.backent;

import com.mallall.common.Const;
import com.mallall.common.Result;
import com.mallall.pojo.User;
import com.mallall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @program: mall
 * @author: qcc
 * @description UserManagerController
 * @create: 2019-02-18 09:04
 * @Version: 1.0
 **/
@RestController
@RequestMapping(value = "/manager/user")
public class UserManagerController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public Result<User> login(User user, HttpSession session){
        Result<User> userResult = iUserService.login(user);
        if(userResult.isSuccess()){
            User data = userResult.getData();
            if(data.getRole() == Const.Role.ROLE_ADMIN){
                //登录的是管理员
                session.setAttribute(Const.CURRENT_USER,data);
                return userResult;
            }else{
                return Result.createByErrorMessage("不是管理员,无法登录");
            }
        }
        return userResult;
    }
}
