package com.weilt.userservice.controller.protal;

import com.weilt.commonentity.commonentity.Const;
import com.weilt.commonentity.commonentity.ResponseCode;
import com.weilt.commonentity.commonentity.ServerResponse;
import com.weilt.commonentity.entity.User;
import com.weilt.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author weilt
 * @com.weilt.userservice.controller
 * @date 2018/9/10 == 1:53
 */
@RestController
public class UserController {
    @Autowired
    private IUserService iUserService;




    /**
     * 用户登录接口
     * @param userName
     * @param password
     * @param session
     * @return
     */
//    @ApiOperation(value = "前台用户登录",notes = "只能采用post方法")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userName",value = "用户名",defaultValue = "无",required = true,dataType = "String"),
//            @ApiImplicitParam(name = "password",value = "密码",defaultValue = "无",required = true,dataType = "String"),
//            @ApiImplicitParam(name = "session",value = "session用来存储用户信息",defaultValue = "无",required = true,dataType = "HttpSession")})
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ServerResponse<User> login(String userName, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(userName,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 登出
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping(value = "/register")
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }


    @PostMapping(value = "/checkvalid")
    public ServerResponse<String> checkValid(String str,String type){
        return  iUserService.checkValid(str,type);
    }

    @PostMapping(value = "/getuserinfo")
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user!=null){
            return ServerResponse.createBySuccessData(user);
        }
        return ServerResponse.createByErrorMessage(Const.User.NEED_LOGIN);
    }

    @PostMapping(value = "/getforgetquestion")
    public ServerResponse<String> forgetGetQuestion(String userName){
        return iUserService.selectQuestion(userName);
    }

    @PostMapping(value = "/checkanswer")
    public ServerResponse<String> forgetCheckAnswer(String userName,String question,String answer){
        return iUserService.checkAnswer(userName,question,answer);
    }

    @PostMapping(value = "/resetforgetpassword")
    public ServerResponse<String> forgetResetPassword(String userName,String passwordNew,String forgetToken){
        return iUserService.forgetResetPassword(userName,passwordNew,forgetToken);

    }


    @PostMapping(value = "/resetpassword")
    public ServerResponse<String> resetPassword(HttpSession session,String password,String passwordNew){
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage(Const.User.NEED_LOGIN);
        }
        return iUserService.resetPassword(password,passwordNew,user);
    }


    @PostMapping(value = "/updateinfomation")
    public ServerResponse<User> update_infomation(HttpSession session,User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage(Const.User.NEED_LOGIN);
        }
        user.setId(currentUser.getId());
        ServerResponse<User> response = iUserService.updateInfomation(user);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,user);
        }
        return response;
    }


    @PostMapping(value = "/getinfomation")
    public ServerResponse<User> get_infomation(HttpSession session){
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    Const.User.NEED_LOGIN);
        }
        return iUserService.getInfomation(user.getId());
    }
    @GetMapping(value = "/testgetinfomation")
    public ServerResponse<User> testget_infomation(){

        return iUserService.getInfomation(1);
    }
}
