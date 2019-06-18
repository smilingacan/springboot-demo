package com.example.demo.service;

import com.example.demo.entity.Permission;
import com.example.demo.entity.SysUser;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：acan
 * @date ：Created in 2019-06-12 10:12
 * @description：user service
 * @modified By：
 * @version: $
 */

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PermissionMapper permissionMapper;

    public SysUser selectFromId(int id){
        return userMapper.selectFromId(id);
    }

    public SysUser login(String userName, String passWord){
        return  userMapper.login(userName, passWord);
    }

    public SysUser loginForPass(String userName){
        return  userMapper.loginForPass(userName);
    }

    public int register(SysUser sysUser){
        return userMapper.register(sysUser);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser sysUser = userMapper.findUserByName(userName);
        if (sysUser != null) {
            List<Permission> permissions = permissionMapper.findPermissionByUserId(sysUser.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Permission permission : permissions) {
                if (permission != null && permission.getName()!=null) {

                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(sysUser.getUsername(), sysUser.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("user: " + userName + " do not exist!");
        }
    }
}
