package com.github.ghdefe.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author ggdefe
 * @Date 2025/3/4 16:43
 */
@Component
public class PrintConfigValueListener implements ApplicationListener<ApplicationReadyEvent> {


    private static final Logger log = LoggerFactory.getLogger(PrintConfigValueListener.class);

    private final String name;

    @Value("${server.port}")
    private int port;

    public PrintConfigValueListener(@Value("${demo.name}") String name) {
        log.debug("创建bean: {}", name);
        this.name = name;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.debug("配置值: {}", name);
        log.debug("端口值: {}", port);
    }
}

