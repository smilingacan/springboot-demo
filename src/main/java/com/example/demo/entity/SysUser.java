package com.example.demo.entity;

import java.util.List;

/**
 * @author ：acan
 * @date ：Created in 2019-06-12 10:06
 * @description：user entity
 * @modified By：
 * @version: $
 */
public class SysUser {
    private Integer id;
    private String username;
    private String password;
    private String realName;
    private List<Role> roles;

    public SysUser(String userName, String passWord){
        this.username = userName;
        this.password = passWord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String userToString(){
        return "sys_user{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
