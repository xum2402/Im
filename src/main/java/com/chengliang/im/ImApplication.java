package com.chengliang.im;

import com.chengliang.im.server.SocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.chengliang.im.mapper")
@SpringBootApplication
public class ImApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(ImApplication.class, args);
        //将 应用上下文对象 存储到 socketserver 中
        SocketServer.setApplicationContext(configurableApplicationContext);
    }

}
