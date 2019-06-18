package com.example.demo.mapper;

import com.example.demo.entity.SysUser;
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
    SysUser selectFromId(int id);
    SysUser login(String userName, String passWord);
    SysUser loginForPass(String userName);
    int register(SysUser sysUser);
    SysUser findUserByName(String userName);
}
