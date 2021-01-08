package com.syd.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 创建配置类，设置使用哪个userDetailsService实现类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }

    @Bean
    PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 实现自定义登录页
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login.html") //登录
                .loginProcessingUrl("/user/login") //登录访问路径
                .defaultSuccessUrl("/test/index").permitAll() //登录成功后跳转路径
                .and().authorizeRequests()
                .antMatchers("/","/test/hello","/user/login").permitAll() //设置哪些路径可以直接访问，不需要认证
               // .antMatchers("/test/index").hasAuthority("admins") //hasAuthority设置当前访问地址有哪些权限
               // .antMatchers("/test/index").hasAnyAuthority("admins,manager")
                .antMatchers("/test/index").hasRole("sale")
                .antMatchers("/test/index").hasAnyRole("sale,admin")
                .anyRequest().authenticated()
                .and().csrf().disable(); //关闭csrf防护
    }
}
