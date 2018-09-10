package com.weilt.userservice.mapper;

import com.weilt.commonentity.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author weilt
 * @com.weilt.userservice.mapper
 * @date 2018/9/9 == 23:40
 */
@Mapper
public interface UserMapper {
    int addUser(User user);

    int updateByIdSelective(User user);

    int deleteById(Integer id);

    int checkUserName(String userName);

    int checkEmail(String email);

    User selectLogin(@Param("userName") String usrName,@Param("password") String password);

    String selectQuestionByUserName(String userName);

    int checkAnswer(@Param("userName") String userName,@Param("question") String question,@Param("answer") String answer);

    int updatePasswordByUserName(@Param("userName") String userName,@Param("password") String passwordNew);

    int checkPassword(@Param("password") String password,@Param("userId") Integer userId);

    int updateByPrimaryKey(User user);

    int checkEmailByUserId(String email,Integer userId);

    User selectById(Integer userId);
}
