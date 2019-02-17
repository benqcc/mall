package com.mallall.service.impl;

import com.mallall.common.Const;
import com.mallall.common.Result;
import com.mallall.dao.UserMapper;
import com.mallall.pojo.User;
import com.mallall.service.UserService;
import com.mallall.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: mall
 * @author: qcc
 * @description UserServiceImpl
 * @create: 2019-02-17 19:58
 * @Version: 1.0
 **/
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<User> login(User user) {
        int i = userMapper.checkUsername(user.getUsername());
        if(i == 0){
            return Result.createByErrorMessage("用户名不存在");
        }
        String md5Password = MD5Util.MD5EncodeUtf8(user.getPassword());
        User user1 = userMapper.selectLogin(user.getUsername(), md5Password);
        if(user1 == null){
            return Result.createByErrorMessage("密码错误");
        }

        user1.setPassword(StringUtils.EMPTY);
        return Result.createBySuccess("登录成功",user1);
    }

    @Override
    public Result<String> register(User user){
        Result<String> stringResult = this.checkValid(user.getUsername(), Const.USERNAME);
        if(!stringResult.isSuccess()){
            return stringResult;
        }
        Result<String> stringResult1 = this.checkValid(user.getEmail(), Const.EMAIL);
        if(!stringResult1.isSuccess()){
            return stringResult;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int count = userMapper.insert(user);

        if(count ==0){
            return Result.createByErrorMessage("注册失败");
        }
        return Result.createBySuccessMessage("注册成功");
    }

    @Override
    public Result<String> checkValid(String str, String type) {
        if(StringUtils.isNotBlank(type)){
            //开始校验
            if(Const.USERNAME.equals(type)){
                int count = userMapper.checkUsername(str);
                if(count > 0){
                    return Result.createByErrorMessage("用户名已存在");
                }
            }
            if(Const.EMAIL.equals(type)){
                int count = userMapper.checkEmail(str);
                if( count > 0){
                    return Result.createByErrorMessage("email已存在");
                }
            }

        }else{
            return Result.createByErrorMessage("参数错误");
        }
        return Result.createBySuccessMessage("校验成功");
    }
}
