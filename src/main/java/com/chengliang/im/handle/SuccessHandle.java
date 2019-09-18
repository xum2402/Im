package com.chengliang.im.handle;

import com.chengliang.im.bean.JsonResult;
import com.chengliang.im.bean.User;
import com.chengliang.im.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chengliang
 */
@Slf4j
@Component
public class SuccessHandle implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.info("验证成功，返回json数据 {}",authentication.getPrincipal());
        httpServletResponse.setCharacterEncoding("utf-8");
        //内容类型 html    json
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JsonResult.success("登陆成功").append("token",JwtUtils.createJwt((User)authentication.getPrincipal())).toJsonString());
        httpServletResponse.getWriter().close();
    }

}
