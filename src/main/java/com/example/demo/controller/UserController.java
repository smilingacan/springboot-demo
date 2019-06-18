package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：acan
 * @date ：Created in 2019-06-12 10:20
 * @description：user controller
 * @modified By：
 * @version: $
 */

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    @RequestMapping("/index")
    public String toIndex(){
        return "index";
    }

    @ResponseBody
    @RequestMapping("/login")
    public String login(User user, HttpServletRequest httpServletRequest){
        User u =  userService.login(user.getUserName(), user.getPassWord());
        if(u == null){
            return "用户名密码错误";
        }else {
            httpServletRequest.getSession().setAttribute("session_user", user);
            return "用户登录成功";
        }
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @RequestMapping("/toRegister")
    public String toRegister() throws IOException{
        return "register";
    }

    @RequestMapping("/register")
    public String register(User user){
        int u = userService.register(user);
        if(u == 0){
            System.out.println("注册失败");
            return "register";
        }
        return "welcome";
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException{
        httpServletRequest.getSession().removeAttribute("session_user");
        httpServletResponse.sendRedirect("/user/index");
    }

    @ResponseBody
    @RequestMapping("getUser/{id}")
    public String getUser(@PathVariable int id){
        return userService.selectFromId(id).userToString();
    }
}
