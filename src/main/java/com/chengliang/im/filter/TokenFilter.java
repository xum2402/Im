package com.chengliang.im.filter;

import com.alibaba.fastjson.JSONObject;
import com.chengliang.im.bean.JsonResult;
import com.chengliang.im.bean.User;
import com.chengliang.im.util.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chengliang
 * 每次发出请求都会执行该过滤器
 * 检查token是否有效。过期、失效等状态
 */
@Slf4j
public class TokenFilter  extends BasicAuthenticationFilter {

    private String tokenName="token";

    public TokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

//    public TokenFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
//        super(requiresAuthenticationRequestMatcher);
//    }
//
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
//        //获取token
//        String token = httpServletRequest.getHeader(tokenName);
//        if(token == null){
//            token = httpServletRequest.getParameter(tokenName);
//        }
//        //
//        if(token == null){
//            logger.info("token不存在");
//            throw new AuthenticationCredentialsNotFoundException("token不存在");
//        }
//        User user=null;
//        try {
//            Claims claims = JwtUtils.parseJwt(token);
//            user= JSONObject.parseObject(claims.getSubject(),User.class);
//        } catch (Exception e) {
//            throw new CredentialsExpiredException("token解析失败");
//        }
//        log.info("token解析成功后的内容{}",user);
//        //实例化安全框架用户对象
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(user,user.getUserPassword(),user.getAuthorities());
//        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//                                            Authentication auth) throws IOException, ServletException
//    {
//        logger.info("验证成功,生成token");
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(auth);
//        SecurityContextHolder.setContext(context);
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                              AuthenticationException authException) throws IOException, ServletException
//    {
//        //输出异常
//        authException.printStackTrace();
//        SecurityContextHolder.clearContext();
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        String message;
//        if (authException.getCause() != null)
//        {
//            message = authException.getCause().getMessage();
//        }
//        else
//        {
//            message = authException.getMessage();
//        }
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/json;charset=utf-8");
//        response.getWriter().write(JsonResult.error(message).toJsonString());
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取token  在请求头中存储token
        String token = httpServletRequest.getHeader(tokenName);
        if(token == null){
            //传递参数的形式传递token  token=asdfasdfsdf412412312
            token = httpServletRequest.getParameter(tokenName);
        }
        //没有token情况
        if(token == null){
            logger.info("token不存在");
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.getWriter().write(JsonResult.error("token不存在").toJsonString());
            return;
            //throw new AuthenticationCredentialsNotFoundException("token不存在");
        }
        User user=null;
        try {
            //解析token 发生异常: token格式不正确、token已失效
            Claims claims = JwtUtils.parseJwt(token);
            //json字符串 将 字符串转换为User对象
            user= JSONObject.parseObject(claims.getSubject(),User.class);
        } catch (Exception e) {
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.getWriter().write(JsonResult.error("token已失效").toJsonString());
            return;
            //throw new CredentialsExpiredException("token解析失败");
        }
        log.info("token解析成功后的内容{}",user);
        //封装安全框架的token对象
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        //将token存储到安全框架的上下文中 ServletContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //放行请求
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }


}
