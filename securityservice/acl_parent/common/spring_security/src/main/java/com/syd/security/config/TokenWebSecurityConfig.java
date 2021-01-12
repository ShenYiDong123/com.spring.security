package com.syd.security.config;

import com.syd.security.filter.TokenAuthFilter;
import com.syd.security.filter.TokenLoginFilter;
import com.syd.security.security.DefaultPasswordEncoder;
import com.syd.security.security.TokenLogoutHandler;
import com.syd.security.security.TokenManager;
import com.syd.security.security.UnauthEntryPoint;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
@Configuration
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;
    private DefaultPasswordEncoder defaultPasswordEncoder;
    private UserDetailsService userDetailsService;
    @Autowired
    public TokenWebSecurityConfig(TokenManager tokenManager,RedisTemplate redisTemplate,
                                  DefaultPasswordEncoder defaultPasswordEncoder,UserDetailsService userDetailsService){
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.userDetailsService = userDetailsService;
    }

    /**
     * 配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new UnauthEntryPoint()) //没有权限访问
                .and().csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/admin/acl/index/logout") //退出路径
                .addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplate)).and() //退出处理类
                .addFilter(new TokenLoginFilter(authenticationManager(),tokenManager,redisTemplate)) //添加认证过滤器
                .addFilter(new TokenAuthFilter(authenticationManager(),tokenManager,redisTemplate)).httpBasic(); //添加授权过滤器
    }

    //调用userDetailService和密码处理
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    //配置不进行认证的路径，可以直接访问
    @Override
    public void configure(WebSecurity webSecurity) throws Exception{
        webSecurity.ignoring().antMatchers("/api/**");
    }
}
