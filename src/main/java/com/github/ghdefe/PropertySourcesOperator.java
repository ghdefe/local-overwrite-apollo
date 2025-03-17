package com.github.ghdefe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author ggdefe
 * @Date 2025/3/12 16:58
 */
public class PropertySourcesOperator {

    private static final Logger log = LoggerFactory.getLogger(PropertySourcesOperator.class);

    public static void addPropertySourceToFirst(ConfigurableEnvironment environment) {
        String osName = System.getProperty("os.name");
        // 仅在window下生效
        if (osName == null || !osName.toLowerCase().contains("window")) {
            log.debug("current os is: {}, not window, skip", osName);
            return;
        }
        List<PropertySource<?>> localPropertySources = LocalPropertiesPropertySourceFactory.getPropertySource();
        if (localPropertySources == null) {
            log.debug("localPropertySources is null, skip");
            return;
        }

        // 将配置插入到所有现有PropertySource的最前端
        MutablePropertySources propertySources = environment.getPropertySources();
        if (log.isDebugEnabled()) {
            log.debug("addPropertySourceToFirst: {}", localPropertySources.stream().map(PropertySource::getName).collect(Collectors.joining(", ")));
        }
        localPropertySources.forEach(propertySources::addFirst);
    }
}
