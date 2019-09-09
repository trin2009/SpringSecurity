package com.example.demo.service;

import com.example.demo.pojo.SysUser;
import com.example.demo.pojo.Token;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class MyTokenService {
    /**
     * token为key
     */
    private static Map<String, Token> tokenInfo = new HashMap<String, Token>();
    /**
     * username为key
     */
    private static Map<String, String> userToken = new HashMap<String, String>();

    private final String secret = "mySecret";

    /**
     * 1小时（单位毫秒）
     */
    private final long expire = 1 * 60 * 60 * 1000;

    public SysUser findUserByToken(String tokenStr) {
        Token token = MyTokenService.tokenInfo.get(tokenStr);
        if (token != null) {
            if (token.getExpireTime() > System.currentTimeMillis()) {
                return token.getSysUser();
            } else {
                removeToken(tokenStr);
            }
        }
        return null;
    }

    public String findTokenByUsername(String username) {
         String tokenStr = MyTokenService.userToken.get(username);
        if(tokenStr !=null){
            Token token = MyTokenService.tokenInfo.get(tokenStr);
            if(token.getExpireTime() > System.currentTimeMillis()){
                return tokenStr;
            }else{
                removeTokenByUsername(username);
            }
        }
        return null;
    }

    /**
     * 缓存用户信息
     *
     * @param user
     */
    public void cacheUser(SysUser user) {
        SysUser cachedUser = findUserByToken(findTokenByUsername(user.getUsername()));
        if(cachedUser == null){
            long expireTime = System.currentTimeMillis() + expire;
            Token token = new Token(user, expireTime);
            String tokenStr = genTokenStr(token);
            MyTokenService.tokenInfo.put(tokenStr, token);
            MyTokenService.userToken.put(user.getUsername(), tokenStr);
        }
    }

    private String genTokenStr(Token token) {
        StringBuilder sb = new StringBuilder();
        sb.append(token.getSysUser().getUsername());
        sb.append(token.getExpireTime());
        String tokenStr = sha256_HMAC(sb.toString(), secret);
        return tokenStr;
    }

    public void removeToken(String tokenStr) {
        Token token = MyTokenService.tokenInfo.get(tokenStr);
        if (token != null) {
            MyTokenService.userToken.remove(token.getSysUser().getUsername());
            MyTokenService.tokenInfo.remove(tokenStr);
        }
    }

    public void removeTokenByUsername(String username) {
        String tokenStr = MyTokenService.userToken.get(username);
        if (tokenStr != null) {
            MyTokenService.tokenInfo.remove(tokenStr);
            MyTokenService.userToken.remove(username);
        }
    }

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * sha256_HMAC加密
     *
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    public static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }
}
