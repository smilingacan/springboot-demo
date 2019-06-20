package com.example.demo.controller;

import com.example.demo.entity.SysUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import springfox.documentation.annotations.ApiIgnore;

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

@Api
@Controller
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiIgnore//使用该注解忽略这个API
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @ApiOperation(value="进入登录页面", notes="进入登录页面")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

//    @PostMapping("/login")
//    public ModelAndView login(SysUser sysUser, HttpServletRequest httpServletRequest){
//        String passEncode = passwordEncoder.encode(sysUser.getPassword());
//        SysUser userInDB = userService.loginForPass(sysUser.getUsername());
//
//        Boolean ifLoginSuccess = passwordEncoder.matches(sysUser.getPassword(), userInDB.getPassword());
//        if(ifLoginSuccess){
//            httpServletRequest.getSession().setAttribute("session_user", sysUser);
//            ModelAndView mav = new ModelAndView("welcome");  //根据不同逻辑返回不同页面或者json
//            return mav;
//        }else {
//            ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
//            mav.addObject("result", "failed");
//            return mav;
//        }
//    }

    @ApiOperation(value="进入注册页面", notes="进入注册页面")
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String toRegister(){
        return "register";
    }

    @ApiOperation(value="注册", notes="输入用户名密码进行注册")
    @ApiImplicitParam(name = "sysUser", value = "用户实体SysUser", required = true, dataType = "SysUser")
    @PostMapping("/register")
    public String register(SysUser sysUser){
        if((sysUser.getUsername() == null) || (sysUser.getPassword()) == null){
            return "register";
        }else {
            int u = userService.register(new SysUser(sysUser.getUsername(), passwordEncoder.encode(sysUser.getPassword())));
            if(u == 0){
                System.out.println("注册失败");
                return "register";
            }
            return "login";
        }
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException{
        httpServletRequest.getSession().removeAttribute("session_user");
        httpServletResponse.sendRedirect("/index");
    }

    //dataType默认是String，不需要设置成Integer
    @ApiOperation(value="获取用户信息", notes="根据用户ID获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true)
    @ResponseBody
    @RequestMapping(value = "/user/getUser/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable Integer id){
        return userService.selectFromId(id).userToString();
    }

    @GetMapping("/admin")
    @ResponseBody
    public String hello(){
        return "hello admin";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public String getList(){
        return "hello getList";
    }
}


//@ApiOperation(value="更新信息", notes="根据url的id来指定更新用户信息")
//@ApiImplicitParams({
//        @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long",paramType = "path"),
//        @ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "User")
//})
//
//swagger通过注解表明该接口会生成文档，包括接口名、请求方法、参数、返回信息的等等。
//
//@Api：修饰整个类，描述Controller的作用
//@ApiOperation：描述一个类的一个方法，或者说一个接口
//@ApiParam：单个参数描述
//@ApiModel：用对象来接收参数
//@ApiProperty：用对象接收参数时，描述对象的一个字段
//@ApiResponse：HTTP响应其中1个描述
//@ApiResponses：HTTP响应整体描述
//@ApiIgnore：使用该注解忽略这个API
//@ApiError ：发生错误返回的信息
//@ApiImplicitParam：一个请求参数
//@ApiImplicitParams：多个请求参数
