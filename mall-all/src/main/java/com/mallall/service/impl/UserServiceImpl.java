package com.mallall.service.impl;

import com.mallall.common.Const;
import com.mallall.common.Result;
import com.mallall.common.TokenCache;
import com.mallall.dao.UserMapper;
import com.mallall.pojo.User;
import com.mallall.service.IUserService;
import com.mallall.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @program: mall
 * @author: qcc
 * @description UserServiceImpl
 * @create: 2019-02-17 19:58
 * @Version: 1.0
 **/
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<User> login(User user) {
        int i = userMapper.checkUsername(user.getUsername());
        if (i == 0) {
            return Result.createByErrorMessage("用户名不存在");
        }
        String md5Password = MD5Util.MD5EncodeUtf8(user.getPassword());
        User user1 = userMapper.selectLogin(user.getUsername(), md5Password);
        if (user1 == null) {
            return Result.createByErrorMessage("密码错误");
        }


        user1.setPassword(StringUtils.EMPTY);
        return Result.createBySuccess("登录成功", user1);
    }

    @Override
    public Result<String> register(User user) {
        Result<String> stringResult = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!stringResult.isSuccess()) {
            return stringResult;
        }
        stringResult = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!stringResult.isSuccess()) {
            return stringResult;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int count = userMapper.insert(user);

        if (count == 0) {
            return Result.createByErrorMessage("注册失败");
        }
        return Result.createBySuccessMessage("注册成功");
    }

    @Override
    public Result<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            //开始校验
            if (Const.USERNAME.equals(type)) {
                int count = userMapper.checkUsername(str);
                if (count > 0) {
                    return Result.createByErrorMessage("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                int count = userMapper.checkEmail(str);
                if (count > 0) {
                    return Result.createByErrorMessage("email已存在");
                }
            }

        } else {
            return Result.createByErrorMessage("参数错误");
        }
        return Result.createBySuccessMessage("校验成功");
    }

    @Override
    public Result<String> selectQuestion(String userName) {
        Result<String> stringResult = this.checkValid(userName, Const.USERNAME);
        if (stringResult.isSuccess()) {
            //用户不存在
            return Result.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(userName);
        if (StringUtils.isNotBlank(question)) {
            return Result.createBySuccess(question);
        }
        return Result.createByErrorMessage("找回密码的问题是空的");
    }

    @Override
    public Result<String> checkAnswer(String userName, String question, String answer) {
        int resultCount = userMapper.checkAnswer(userName, question, answer);
        if (resultCount > 0) {
            //说明问题及问题答案是这个用户的,并且是正确的
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + userName, forgetToken);
            return Result.createBySuccess(forgetToken);
        }
        return Result.createByErrorMessage("问题的答案是错误的");
    }

    @Override
    public Result<String> forgetRestPassword(String userName, String passwordNew, String token) {
        if (StringUtils.isBlank(token)) {
            return Result.createByErrorMessage("参数错误,token不能为空");
        }
        Result<String> stringResult = this.checkValid(userName, Const.USERNAME);
        if (stringResult.isSuccess()) {
            //用户不存在
            return Result.createByErrorMessage("用户不存在");
        }
        String key = TokenCache.getKey(TokenCache.TOKEN_PREFIX + userName);
        if (StringUtils.isBlank(key)) {
            return Result.createByErrorMessage("token无效或者过期");
        }
        if (StringUtils.equals(token, key)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(userName, md5Password);
            if (rowCount > 0) {
                return Result.createBySuccessMessage("修改密码成功");
            }
        } else {
            return Result.createByErrorMessage("token错误,请重新获取重置密码的token");
        }
        return Result.createByErrorMessage("修改密码失败");
    }

    @Override
    public Result<String> restPassword(String passwordOld, String passwordNew, User user) {
        //防止横向越权,要校验一下这个用户的旧密码,一定要指定的是这个用户,因为我们会查询一个count(1),如果不指定id,那么结果就是true,count>0
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (resultCount == 0) {
            return Result.createByErrorMessage("密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0) {
            return Result.createBySuccessMessage("密码更新成功");
        }
        return Result.createByErrorMessage("密码更新失败");
    }

    @Override
    public Result<User> updateInformation(User user) {
        //userName不能被更新,email是不是已经存在,并且存在的email如果相同的话,不能是当前用户的
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (resultCount > 0) {
            return Result.createByErrorMessage("email已存在,请更换email再重试");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(updateUser.getQuestion());
        updateUser.setAnswer(updateUser.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0) {
            return Result.createBySuccess("更新个人信息成功", updateUser);
        }
        return Result.createByErrorMessage("更新个人信息失败");
    }
}
