package tw.com.younite.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import tw.com.younite.config.SpringSecurityConfig;
import tw.com.younite.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * token的校驗
 * 該類繼承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 從http頭的Authorization 項讀取token數據，然後用Jwts包提供的方法校驗token的合法性。
 * 如果校驗通過，就認為這是一個取得授權的合法請求
 * @author xxm
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String url = request.getRequestURI();
        String header = request.getHeader(JwtUtil.AUTHORIZATION);

        JSONObject json = new JSONObject();
        //跳過不需要驗證的路徑
        if (null != SpringSecurityConfig.AUTH_WHITELIST && Arrays.asList(SpringSecurityConfig.AUTH_WHITELIST).contains(url)) {
            chain.doFilter(request, response);
            return;
        }
        if (StringUtils.isBlank(header) || !header.startsWith(JwtUtil.TOKEN_PREFIX)) {

            json.put("codeCheck", false);
            json.put("msg", "Token為空");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(json));
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            //json.put("status", "-2");
            json.put("codeCheck", false);
            json.put("msg", "Token已過期");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(json));
            logger.error("Token已過期: {} " + e);
        } catch (UnsupportedJwtException e) {
            //json.put("status", "-3");
            json.put("codeCheck", false);
            json.put("msg", "Token格式錯誤");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(json));
            logger.error("Token格式錯誤: {} " + e);
        } catch (MalformedJwtException e) {
            //json.put("status", "-4");
            json.put("codeCheck", false);
            json.put("msg", "Token沒有被正確構造");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(json));
            logger.error("Token沒有被正確構造: {} " + e);
        } catch (SignatureException e) {
            //json.put("status", "-5");
            json.put("codeCheck", false);
            json.put("msg", "Token簽名失敗");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(json));
            logger.error("簽名失敗: {} " + e);
        } catch (IllegalArgumentException e) {
            //json.put("status", "-6");
            json.put("codeCheck", false);
            json.put("msg", "Token非法參數異常");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(json));
            logger.error("非法參數異常: {} " + e);
        } catch (Exception e) {
            //json.put("status", "-9");
            json.put("codeCheck", false);
            json.put("msg", "Invalid Token");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(json));
            logger.error("Invalid Token " + e.getMessage());
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(JwtUtil.AUTHORIZATION);
        if (token != null) {
            String userName = "";

            try {
                // 解密Token
                userName = JwtUtil.validateToken(token);
                if (StringUtils.isNotBlank(userName)) {
                    return new UsernamePasswordAuthenticationToken(userName, null, new ArrayList<>());
                }
            } catch (ExpiredJwtException e) {
                throw e;
                //throw new TokenException("Token已過期");
            } catch (UnsupportedJwtException e) {
                throw e;
                //throw new TokenException("Token格式錯誤");
            } catch (MalformedJwtException e) {
                throw e;
                //throw new TokenException("Token沒有被正確構造");
            } catch (SignatureException e) {
                throw e;
                //throw new TokenException("簽名失敗");
            } catch (IllegalArgumentException e) {
                throw e;
                //throw new TokenException("非法參數異常");
            } catch (Exception e) {
                throw e;
                //throw new IllegalStateException("Invalid Token. "+e.getMessage());
            }
            return null;
        }
        return null;
    }
}