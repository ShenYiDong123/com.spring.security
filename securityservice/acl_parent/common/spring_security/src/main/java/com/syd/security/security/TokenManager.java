package com.syd.security.security;

import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * token工具类
 */
@Component
public class TokenManager {

    //token有效时长
    private String tokenEcpiration = "24*60*60*1000";
    //编码秘钥
    private String tokenSignKey = "123456";

    //1. 根据用户名生成token
    public String createToken(String username){
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+tokenEcpiration))
                .signWith(SignatureAlgorithm.HS512,tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    //2. 根据用户名生成token
    public String getUserInfoFromToken(String token){
        String userinfo = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return userinfo;
    }

    //3. 删除token
    public void removeToken(String token){

    }
}
