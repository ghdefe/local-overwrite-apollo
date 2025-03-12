package com.github.ghdefe;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author cmz.zeng
 * @Date 2025/3/4 16:43
 */
@Component
public class PrintConfigValueListener implements ApplicationListener<ApplicationReadyEvent> {


    private static final Logger log = LoggerFactory.getLogger(PrintConfigValueListener.class);

    private final String name;

    public PrintConfigValueListener(@Value("${demo.name}") String name) {
        log.debug("创建bean: {}", name);
        this.name = name;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.debug("配置值: {}", name);
    }
}

