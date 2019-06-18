package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：acan
 * @date ：Created in 2019-06-12 10:12
 * @description：user service
 * @modified By：
 * @version: $
 */

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User selectFromId(int id){
        return userMapper.selectFromId(id);
    }

    public User login(String userName, String passWord){
        return  userMapper.login(userName, passWord);
    }

    public int register(User user){
        return userMapper.register(user);
    }
}
