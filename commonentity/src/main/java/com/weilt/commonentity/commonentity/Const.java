package com.weilt.commonentity.commonentity;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author weilt
 * @com.weilt.commonentity.commonentity
 * @date 2018/9/9 == 22:47
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }
    public interface Cart{
        int CHECKED = 1;//购物车选中
        int UN_CHECKED = 0; //购物车未选中
        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";


    }

    public interface User{
        String FAILUE_REGISTER="注册失败";
        String SUCCESS_REGISTER="注册成功";
        String SAME_USERNAME="用户名已存在";
        String REGISTER_EMAIL="Email已注册";
        String ILLEGE_ARGUMENT = "参数错误";
        String SUCCESS_CHECKED="校验成功";
        String NO_USER="无此用户";
        String NO_QUESTION ="找回密码问题为空！！";
        String ERROR_ANSWER="问题答案不符合！";
        String NEED_TOKEN="需要传递Token!!";
        String ERROR_TOKEN="token无效或者过期";
        String SUCCESS_UPDATE_PASSWORD="修改密码成功";
        String FAILUE_UPDATE_PASSWORD="修改密码失败，请重试！！！";
        String ERROR_OLD_PASSWORD="原密码错误！！！";
        String SUCCESS_UPDATE="更新成功！！";
        String FAILUE_UPDATE="更新失败";
    }

    public static final String EMAIL="email";
    public static final String USERNAME="username";


    public interface Role{
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;   //管理员
    }

    public enum ProductStatusEnum{
        ON_SALE(1,"在线");
        private String value;
        private int code;

        ProductStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }


        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }
}
