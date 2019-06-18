package com.example.demo.controller;

import com.example.demo.entity.SysUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

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
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(SysUser sysUser, HttpServletRequest httpServletRequest){
        String passEncode = passwordEncoder.encode(sysUser.getPassword());
        SysUser userInDB = userService.loginForPass(sysUser.getUsername());

        Boolean ifLoginSuccess = passwordEncoder.matches(sysUser.getPassword(), userInDB.getPassword());
        if(ifLoginSuccess){
            httpServletRequest.getSession().setAttribute("session_user", sysUser);
            ModelAndView mav = new ModelAndView("index");  //根据不同逻辑返回不同页面或者json
            return mav;
        }else {
            ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
            mav.addObject("result", "failed");
            return mav;
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String toRegister(){
        return "register";
    }

    @PostMapping("/register")
    public String register(SysUser sysUser){
        int u = userService.register(new SysUser(sysUser.getUsername(), passwordEncoder.encode(sysUser.getPassword())));
        if(u == 0){
            System.out.println("注册失败");
            return "register";
        }
        return "index";
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException{
        httpServletRequest.getSession().removeAttribute("session_user");
        httpServletResponse.sendRedirect("/login");
    }

    @ResponseBody
    @RequestMapping("getUser/{id}")
    public String getUser(@PathVariable int id){
        return userService.selectFromId(id).userToString();
    }
}
