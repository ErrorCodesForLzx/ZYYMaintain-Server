package com.errorcodes.zyy_maintain.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

@Configuration
public class WebSocketConfiguration implements ServletContainerInitializer {

    /**
     * 用于扫描带有@ServerEndPoint的注解成为webSocket
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {

    }
}
