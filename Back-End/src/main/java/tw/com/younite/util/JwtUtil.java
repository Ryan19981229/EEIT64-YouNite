package tw.com.younite.util;

import io.jsonwebtoken.*;
import tw.com.younite.entity.UserEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//處理JWT（JSON Web Token）的生成和驗證的工具類
public class JwtUtil {
    /**過期時間---24 hour*/
    private static final int EXPIRATION_TIME = 60*60*24;
    /**自己設定的秘鑰*/
    private static final String SECRET = "012345678909876543211213213123012345678909876543211213213123012345678909876543211213213123";
    /**前綴*/
    public static final String TOKEN_PREFIX = "Bearer";
    /**表頭授權*/
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 功能描述:創建Token
     *
     */
    public static String generateToken(UserEntity user) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        // 設置簽發時間
        calendar.setTime(new Date());
        // 設置過期時間
        // 添加秒鐘
        calendar.add(Calendar.SECOND, EXPIRATION_TIME);
        Date time = calendar.getTime();
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", user);
        String jwt = Jwts.builder()
                .setClaims(map)
                //簽發時間
                .setIssuedAt(now)
                //過期時間
                .setExpiration(time)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        //jwt前面一般都會加Bearer
        return TOKEN_PREFIX + jwt;
    }
    /**
     *
     * @author: xxm
     * 功能描述: 解密Token
     * @date: 2020/5/28 16:18
     * @param:
     * @return:
     */
    public static String validateToken(String token) {
        try {
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            String user = body.get("user").toString();
            return user;
        }catch (ExpiredJwtException e) {
            throw e;
        } catch (UnsupportedJwtException e) {
            throw e;
        } catch (MalformedJwtException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e){
            throw e;
        }
    }
}
