package com.chengliang.im.config;

import com.chengliang.im.filter.JwtLoginFilter;
import com.chengliang.im.filter.TokenFilter;
import com.chengliang.im.handle.FailureHandler;
import com.chengliang.im.handle.SuccessHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author chengliang
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SuccessHandle successHandle;

    @Autowired
    private FailureHandler failureHandler;


    /**
     * 实例化tokenFilter
     * @return
     */
    @Bean
    public TokenFilter tokenFilter() throws Exception {
        // login 该接口是出于放行状态的
       //TokenFilter tokenFilter = new TokenFilter();
        //tokenFilter.setAuthenticationManager(authenticationManagerBean());
        return new TokenFilter(authenticationManager());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 处理跨域请求中的Preflight请求
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                //任意的请求都需要进行验证
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/login")
                .successHandler(successHandle)
                .failureHandler(failureHandler)
                .and()
                //.loginProcessingUrl("/user/login")
                //.and()
                //在用户名密码效验之前，执行tokenfilter
                .addFilter(new JwtLoginFilter(authenticationManager()))
                .addFilter(tokenFilter())
                //去掉session的创建管理
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //跨域请求问题
        http.cors();
        //csrf问题
        http.csrf().disable();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //密码加密/解密
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
