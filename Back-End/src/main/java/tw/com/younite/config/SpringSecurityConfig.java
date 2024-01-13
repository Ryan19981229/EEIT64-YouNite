package tw.com.younite.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import tw.com.younite.filter.JWTAuthenticationFilter;


import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 需要放行的URL
     */


    public static final String[] AUTH_WHITELIST = {
            "/users/**","/storage/**","/orders/**","/employees/**","/management/**","/callback/**","/home/**","/users/register","/users/profile","/redirectPost","/items","/mail/**",
            "/websocket/**", "/msgServer2/**",
            "/client.html","/message/add","/storage/uploadChat","/js/conn.js"
    };
    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTokenRepositoryImpl tokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

//    @Bean
//    UserDetailsService customUserService() { // 注册UserDetailsService 的bean
//        return new CustomUserServiceImpl();
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserService()).passwordEncoder(new BCryptPasswordEncoder());
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * 配置請求攔截
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                //由於使用的是JWT，所以這裡不需要csrf
                .csrf().disable()
                //因為token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                //可以匿名訪問的url
                .antMatchers(AUTH_WHITELIST).permitAll()
                //其他所有請求需要身份認證
                .anyRequest().authenticated()

                .and()
                //.addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new JWTAuthenticationFilter(authenticationManager()));
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST);
        super.configure(web);
    }
}
