package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author ：acan
 * @date ：Created in 2019-06-12 10:13
 * @description：user mapper
 * @modified By：
 * @version: $
 */

@Repository
public interface UserMapper {
    User selectFromId(int id);
    User login(String userName, String passWord);
    int register(User user);
}
