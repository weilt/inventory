package com.weilt.userservice.service;

import com.weilt.commonentity.commonentity.ServerResponse;
import com.weilt.commonentity.entity.User;

/**
 * @author weilt
 * @com.weilt.eshopuser.service
 * @date 2018/8/21 == 0:44
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse selectQuestion(String username);

    ServerResponse<String> checkAnswer(String userName, String question, String answer);

    ServerResponse<String> forgetResetPassword(String userName, String passwordNew, String forgetToken);

    ServerResponse<String> resetPassword(String password, String passwordNew, User user);

    ServerResponse<User> updateInfomation(User user);

    ServerResponse<User> getInfomation(Integer userId);

}
