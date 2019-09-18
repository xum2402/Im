package com.chengliang.im.util;

import com.alibaba.fastjson.JSONObject;
import com.chengliang.im.bean.User;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Date;

/**
 * jwttoken工具类
 * token的加密 与 解密
 * @author chengliang
 */
public class JwtUtils {

    private static final String KEY="asdf313eqwf12%$#47#%8$#95*&^%$#";

    /**
     * 生成token  jwt
     * @param user user对象
     * @return token字符串
     */
    public static String  createJwt(User user){
        //签名方式、加密方式
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //当前时间
        long currentTimeMillis = System.currentTimeMillis();
        Date date=new Date(currentTimeMillis);
        //实例化jwt对象
        JwtBuilder jwtBuilder = Jwts.builder()
                //唯一ID
                .setId(user.getUserId().toString())
                //用户信息
                .setSubject(JSONObject.toJSONString(user))
                .setIssuer(user.getUserNickName())
                .setIssuedAt(date)
                .signWith(signatureAlgorithm, KEY)
                //设置过期时间
                //30秒
                .setExpiration(new Date(currentTimeMillis + 1000 * 60 *30));
        //生成token
        return jwtBuilder.compact();
    }

    /**
     * 解析jwt
     * @param jwt
     * @return
     */
    public static Claims parseJwt(String jwt){
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(jwt)
                .getBody();
    }


    public static void main(String[] args) {
        User user = new User();
        user.setUserId(10);
        user.setUserNickName("张三");
        String jwt = createJwt(user);
//        System.out.println(jwt);
        Claims claims = null;
        try {
            claims = parseJwt("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMCIsInN1YiI6IuW8oOS4iSIsImlzcyI6IuW8oOS4iSIsImlhdCI6MTU2ODE2NjU0MCwiZXhwIjoxNTY4MTY2NTcwfQ.0HPLn5Aygc0pt8ts_PE2bmBFAl_IQ-Fm8tsxzSYr7ck");
        }
        catch (ExpiredJwtException e){
            e.printStackTrace();
            System.out.println("token已过期");
        }
        catch (Exception e) {
            System.out.println("解析jwt失败");
            e.printStackTrace();
        }
        //System.out.println(claims.get("sub"));
    }



}
