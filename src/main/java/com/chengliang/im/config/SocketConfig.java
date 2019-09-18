package com.chengliang.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author chengliang
 * socket配置类
 */
@Configuration
public class SocketConfig {

    /**
     * 讲该对象添加到ioc容器中
     * @return
     * 服务端输出对象
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
