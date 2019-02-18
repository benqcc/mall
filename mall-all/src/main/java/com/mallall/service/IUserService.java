package com.mallall.service;

import com.mallall.common.Result;
import com.mallall.pojo.User;

/**
 * @program: mall
 * @author: qcc
 * @description UserImpl
 * @create: 2019-02-17 19:49
 * @Version: 1.0
 **/
public interface IUserService {

    Result<User> login(User user);

    Result<String> register(User user);

    Result<String> checkValid(String str,String type);

    Result<String> selectQuestion(String userName);

    Result<String> checkAnswer(String userName,String question,String answer);

    Result<String> forgetRestPassword(String userName,String passwordNew,String token);

    Result<String> restPassword(String passwordOld,String passwordNew,User user);

    Result<User> updateInformation(User user);
}
