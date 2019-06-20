package com.example.demo.config;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;


/**
 * @program: demo
 * @description: security config
 * @author: acan
 * @create: 2019-06-18 10:10
 **/

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Bean
    UserDetailsService customUserService(){ //注册UserDetailsService 的bean
        return new UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(passwordEncoder());; //user Details Service验证
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.authorizeRequests()每个匹配器按照它们被声明的顺序被考虑。
        http.rememberMe()//开启cookie保存用户数据
            //设置cookie有效期
            .tokenValiditySeconds(7*24*24*60)
            //设置cookie的私钥
            .key("security")
            .and()
            .authorizeRequests()
            // 所有用户均可访问的资源
            .antMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "**/favicon.ico").permitAll()
            // ROLE_USER的权限才能访问的资源
            .antMatchers("/").permitAll()
            .antMatchers("/register").permitAll()
            .antMatchers("/ws/**").permitAll()
            .antMatchers("/websocket").permitAll()
            // 任何尚未匹配的URL只需要验证用户即可访问
            .anyRequest().authenticated()
            .and()
            .formLogin()
            // 指定登录页面,授予所有用户访问登录页面
            .loginPage("/login")
            //设置默认登录成功跳转页面,错误回到login界面
            .defaultSuccessUrl("/index").failureUrl("/login?error").permitAll()
            .and()
            .logout().permitAll()
            .and()
            .csrf().disable()
            .sessionManagement()
            .invalidSessionUrl("/login")
            .maximumSessions(1)
            .expiredUrl("/login");

        //登录拦截器
//        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
//                //springsecurity4自动开启csrf(跨站请求伪造)与restful冲突
//                .csrf().disable();
    }
}