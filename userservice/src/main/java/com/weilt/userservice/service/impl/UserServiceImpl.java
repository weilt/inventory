package com.weilt.userservice.service.impl;

import com.weilt.commonentity.commonentity.Const;
import com.weilt.commonentity.commonentity.ServerResponse;
import com.weilt.commonentity.commonentity.TokenCache;
import com.weilt.commonentity.entity.User;
import com.weilt.commonentity.utils.MD5Util;
import com.weilt.userservice.mapper.UserMapper;
import com.weilt.userservice.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author weilt
 * @com.weilt.eshopuser.service.impl
 * @date 2018/8/21 == 0:45
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password){
        int resultCount = userMapper.checkUserName(username);
        if(resultCount==0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        MD5Util md5Util = new MD5Util();
        password = md5Util.getmd5(password);

        User user = userMapper.selectLogin(username,password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);


        return  ServerResponse.createBySuccessMessageAndData("登录成功",user);

    }

    @Override
    public ServerResponse<String> register(User user){
//        int resultCount = userMapper.checkUserName(user.getUserName());
//        if(resultCount>0){
//            return ServerResponse.createByErrorMessage("用户名不存在");
//        }
//        resultCount = userMapper.checkEmail(user.getEmail());
//        if(resultCount>0){
//            return  ServerResponse.createByErrorMessage("email已注册");
//        }
        ServerResponse validResponse = this.checkValid(user.getUserName(),Const.USERNAME);
        if(!validResponse.isSuccess()){
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        MD5Util md5Util = new MD5Util();
        user.setPassword(md5Util.getmd5(user.getPassword()));
        int resultCount = userMapper.addUser(user);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage(Const.User.FAILUE_REGISTER);
        }
        return ServerResponse.createBySuccessMessage(Const.User.SUCCESS_REGISTER);
    }

    @Override
    public ServerResponse<String> checkValid(String str,String type){
        if(StringUtils.isNotBlank(type)){
            //开始校验
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUserName(str);
                if(resultCount>0){
                    return ServerResponse.createByErrorMessage(Const.User.SAME_USERNAME);
                }
            }
            if(Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if(resultCount>0){
                    return ServerResponse.createByErrorMessage(Const.User.REGISTER_EMAIL);
                }
            }

        }else {
            return ServerResponse.createByErrorMessage(Const.User.ILLEGE_ARGUMENT);
        }
        return ServerResponse.createBySuccessMessage(Const.User.SUCCESS_CHECKED);
    }

    @Override
    public ServerResponse selectQuestion(String userName){
        ServerResponse validResponse = this.checkValid(userName,Const.USERNAME);
        if(validResponse.isSuccess()){
            //用户不存在
            return  ServerResponse.createByErrorMessage(Const.User.NO_USER);
        }

        String question = userMapper.selectQuestionByUserName(userName);
        if(StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccessMessage(question);
        }else {
            return ServerResponse.createByErrorMessage(Const.User.NO_QUESTION);
        }

    }


    @Override
    public ServerResponse<String> checkAnswer(String userName,String question,String answer){
            int resultCount = userMapper.checkAnswer(userName,question,answer);
            if(resultCount>0){
                //说明问题答案正确，用户符合
                String forgetToken = UUID.randomUUID().toString();
                TokenCache.setKey(TokenCache.TOKEN_PREFIX+userName,forgetToken);
                return ServerResponse.createBySuccessMessage(forgetToken);
            }
            else {
                return ServerResponse.createByErrorMessage(Const.User.ERROR_ANSWER);
            }
    }


    @Override
    public ServerResponse<String> forgetResetPassword(String userName,String passwordNew,String forgetToken)
    {
        if(StringUtils.isNotBlank(forgetToken)){
            return ServerResponse.createByErrorMessage(Const.User.NEED_TOKEN);
        }
        ServerResponse validResponse = this.checkValid(userName,Const.USERNAME);
        if(validResponse.isSuccess()){
            //用户不存在
            return  ServerResponse.createByErrorMessage(Const.User.NO_USER);
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+userName);

        if(StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage(Const.User.ERROR_TOKEN);
        }
        if(StringUtils.equals(forgetToken,token)){
            String md5Password = new MD5Util().getmd5(passwordNew);
            int rowCount = userMapper.updatePasswordByUserName(userName,md5Password);
            if(rowCount>0){
                return ServerResponse.createBySuccessMessage(Const.User.SUCCESS_UPDATE_PASSWORD);
            }
        }else {
            return ServerResponse.createByErrorMessage(Const.User.ERROR_TOKEN);
        }
        return ServerResponse.createByErrorMessage(Const.User.FAILUE_UPDATE_PASSWORD);
    }

    @Override
    public ServerResponse<String> resetPassword(String password,String passwordNew,User user){
        //防止横向越权，需要校验用户的旧密码，一定要指定是这个用户，因为我们查询的是count(1),如果不指定id ,
        //结果一定是>0，也就是ture
        int resultCount = userMapper.checkPassword(password,user.getId());
        if(resultCount==0){
            return ServerResponse.createByErrorMessage(Const.User.ERROR_OLD_PASSWORD);
        }

        user.setPassword(new MD5Util().getmd5(passwordNew));
        int updateCount = userMapper.updateByIdSelective(user);
        if(updateCount>0)
        {
            return ServerResponse.createBySuccessMessage(Const.User.SUCCESS_UPDATE_PASSWORD);
        }
        return ServerResponse.createByErrorMessage(Const.User.FAILUE_UPDATE_PASSWORD);
    }


    @Override
    public ServerResponse<User> updateInfomation(User user){
        //userName 是不能更新的。
        //email也要进行一个校验，校验email是不是已经存在，如果存在的话，不能是当前用户的
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount>0){
            return ServerResponse.createByErrorMessage(Const.User.REGISTER_EMAIL);
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        int updateCount = userMapper.updateByIdSelective(updateUser);
        if(updateCount>0){
            return  ServerResponse.createBySuccessMessage(Const.User.SUCCESS_UPDATE);
        }
        return  ServerResponse.createByErrorMessage(Const.User.FAILUE_UPDATE);
    }

    @Override
    public ServerResponse<User> getInfomation(Integer userId){
        User user = userMapper.selectById(userId);
        if(user == null){
            ServerResponse.createByErrorMessage(Const.User.NO_USER);
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccessData(user);
    }
}
