package com.chengliang.im.filter;

import com.chengliang.im.bean.JsonResult;
import com.chengliang.im.bean.User;
import com.chengliang.im.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chengliang
 * 重写安全框架默认的账号密码验证过滤器，验证成功后的处理方式
 */
@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {


    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Object principal = authResult.getPrincipal();
        httpServletResponse.setCharacterEncoding("utf-8");
        //内容类型 html    json
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JsonResult.success("登陆成功").append("token", JwtUtils.createJwt((User)principal)).toJsonString());
        httpServletResponse.getWriter().close();
    }

}
