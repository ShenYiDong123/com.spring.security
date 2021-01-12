package com.syd.security.security;

import com.syd.utils.utils.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码处理工具类
 */
@Component
public class DefaultPasswordEncoder implements PasswordEncoder {

    public DefaultPasswordEncoder(){
        this(-1);
    }

    public DefaultPasswordEncoder(int strength){
        //this(strength);
    }

    // 进行MD5加密
    @Override
    public String encode(CharSequence charSequence) {
        return MD5.encrypt(charSequence.toString());
    }

    // 进行密码比对
    @Override
    public boolean matches(CharSequence charSequence, String encodeedPassword) {
        return encodeedPassword.equals(MD5.encrypt(charSequence.toString()));
    }
}
