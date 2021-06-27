//package com.syd.service;
//
//import org.junit.Test;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class LoginService implements UserDetailsService {
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 判断用户名是否存在
//        if (!"admin".equals(username)) {
//            throw new UsernameNotFoundException("用户名不存在！");
//        }
//        // 从数据库中获取的密码 atguigu 的密文
//        String pwd = "$2a$10$2R/M6iU3mCZt3ByG7kwYTeeW0w7/UqdeXrb27zkBIizBvAven0/na";
//        // 第三个参数表示权限
//        return new User(username, pwd,
//                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,"));
//    }
//
//
//    @Test
//    public void test01(){
//        // 创建密码解析器
//        BCryptPasswordEncoder bCryptPasswordEncoder = new
//                BCryptPasswordEncoder();
//        // 对密码进行加密
//        String atguigu = bCryptPasswordEncoder.encode("123");
//        // 打印加密之后的数据
//        System.out.println("加密之后数据：\t"+atguigu);
//        //判断原字符加密后和加密之前是否匹配
//        boolean result = bCryptPasswordEncoder.matches("123", atguigu);
//        // 打印比较结果
//        System.out.println("比较结果：\t"+result);
//    }
//}
