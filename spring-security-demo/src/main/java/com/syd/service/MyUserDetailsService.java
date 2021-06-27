package com.syd.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.syd.dao.UserInfoMapper;
import com.syd.dao.UsersMapper;
import com.syd.entity.Menu;
import com.syd.entity.Role;
import com.syd.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * localhost:8080 登录时会进入
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;
    /**
     * 1.设置登录系统的账号、密码
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<Users> wrapper = new QueryWrapper();
        wrapper.eq("username",s);
        Users users = usersMapper.selectOne(wrapper);
        if(users == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        System.out.println(users);

        //获取用户角色及菜单
        List<Role> roleList = userInfoMapper.selectRoleByUserId(Long.valueOf(users.getId()));
        List<Menu> menuList = userInfoMapper.selectMenuByUserId(Long.valueOf(users.getId()));


        //声明一个权限集合List<GrantedAuthority> auths
        List<GrantedAuthority> auths = new ArrayList<>();
        //处理角色
        for (Role role : roleList) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROlE" + role.getName());
            auths.add(simpleGrantedAuthority);
        }

        //处理权限
        for (Menu menu : menuList) {
            auths.add(new SimpleGrantedAuthority(menu.getPermission()));
        }

       //List<GrantedAuthority> auths =
       //         AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin,ROLE_role"); //分配权限
        return new User(users.getUsername(),
                new BCryptPasswordEncoder().encode(users.getPassword()),auths);
    } }