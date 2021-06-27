package com.syd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * security配置类
 */
@Configuration
@EnableWebSecurity//开启Spring Security的功能
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启方法安全级别的控制
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin() //表单登录
//            .and().authorizeRequests() //认证配置
//            .anyRequest() //其他请求
//            .authenticated(); //都需要身份认证
//        http.csrf().disable()//禁用了 csrf 功能
//                .formLogin().loginPage("/login.html")// 自定义登录页面路径
//                .loginProcessingUrl("/authentication/form")// 自定义页面的登录路径，注意要与登录页面的action值一致，<form action="/authentication/form" method="post">
//                .and().authorizeRequests()//限定签名成功的请求
//                .antMatchers("/index").hasAnyRole("EMPLOYEE","ADMIN")//index 下的接口 需要 EMPLOYEE 或者 ADMIN 权限
//                .antMatchers("/test/login").permitAantMatchersll()///employee/login 不限定
//                .antMatchers("/admin/**").hasRole("ADMIN")//对admin下的接口 需要ADMIN权限
//                .antMatchers("/oauth/**").permitAll()//不拦截 oauth 开放的资源
//                .anyRequest().permitAll()//其他没有限定的请求，允许访问
//                .and().anonymous()//对于没有配置权限的其他请求允许匿名访问
//                .and().httpBasic();//启用http 基础验证

        http.authorizeRequests()
                .antMatchers("/html/**","/index") //表示配置请求路径
                .permitAll() // 指定 URL 无需保护。
                .antMatchers("/findAll").hasAnyRole("管理员")//需要用户带有admins权限
                .antMatchers("/find").hasAnyRole("menu:user")//需要主体带有role属性
                .anyRequest() // 其他请求
                .authenticated(); //需要认证

        //设置未授权的请求跳转到登录页
        http.formLogin()
                .loginPage("/html/login.html") // 配置哪个 url 为登录页面
                .loginProcessingUrl("/login") // 设置哪个是登录的 url。
                .successForwardUrl("/success") // 登录成功之后跳转到哪个 url
                .failureForwardUrl("/fail");// 登录失败之后跳转到哪个 url

        //自定义 403 页面
        http.exceptionHandling().accessDeniedPage("/unauth");

        // 关闭 csrf
        http.csrf().disable();
    }

    // 注入 PasswordEncoder 类到 spring 容器中
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}