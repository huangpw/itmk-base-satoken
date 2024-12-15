package com.itmk.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @Author: AlbertHPW
 * @Date: 2024/12/11 0:15
 */
@Component // 将该类交给spring管理
@Data // 生成get/set方法
@ConfigurationProperties(prefix = "jwt") // 读取配置文件
public class JwtUtils {
    // 颁发者
    private String issuer;
    // 密钥
    private String secret;
    // 过期时间 分钟
    private int expiration;

    /**
     * 生成token
     * @param map
     * @return
     */
    public String generateToken(Map<String, String> map) {
        // 设置令牌的过期时间
        Calendar instance = Calendar.getInstance();
        // 设置失效时间
        instance.add(Calendar.MINUTE, expiration);
        // 创建JWT builder
        JWTCreator.Builder builder = JWT.create();
        // payload
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        String token = builder.withIssuer(issuer).withIssuedAt(new Date()).withExpiresAt(instance.getTime()) //指定令牌过期时间
                .sign(Algorithm.HMAC256(secret));
        return token;
    }

    /**
     * 验证令牌是否合法
     * @param token
     * @return
     */
    public boolean verify(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public DecodedJWT jwtDecode(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        } catch (SignatureVerificationException e) {
            throw new RuntimeException("token签名错误！");
        }catch (AlgorithmMismatchException e){
            throw new RuntimeException("token算法不匹配！");
        }catch (TokenExpiredException e) {
            throw new RuntimeException("token已过期！");
        }catch (Exception e){
            throw new RuntimeException("token解析失败！");
        }
    }
}
