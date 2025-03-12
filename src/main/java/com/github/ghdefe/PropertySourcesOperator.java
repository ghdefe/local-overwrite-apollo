package com.github.ghdefe;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * @Author cmz.zeng
 * @Date 2025/3/12 16:58
 */
public class PropertySourcesOperator {

    public static void addPropertySourceToFirst(ConfigurableEnvironment environment) {
        String osName = System.getProperty("os.name");
        // 仅在window下生效
        if (osName == null || !osName.toLowerCase().contains("window")) {
            return;
        }
        PropertySource<?> propertySource = LocalPropertiesFilePropertySourceFactory.getPropertySource();
        if (propertySource == null) {
            return;
        }

        // 将配置插入到所有现有PropertySource的最前端
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(propertySource);
    }
}
