package com.github.ghdefe;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@Component
@Order(1)
public class LocalPropertyLoader implements ApplicationContextInitializer<ConfigurableApplicationContext>, EnvironmentPostProcessor, BeanFactoryPostProcessor, EnvironmentAware {


    private PropertySource propertySource;
    private ConfigurableEnvironment environment;

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        addPropertySourceToFirst(environment);
    }


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        addPropertySourceToFirst(applicationContext.getEnvironment());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        addPropertySourceToFirst(environment);
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }


    private Optional<File> getLocalFile(File dir, int searchLevel) {
        // 读取根目录下的local.env文件
        File file = new File(dir, ".env/local.properties");
        if (file.exists()) {
            return Optional.of(file);
        }
        if (searchLevel <= 0) {
            return Optional.empty();
        }
        // 往上一级找
        File parentDir = file.getParentFile();
        return getLocalFile(parentDir, searchLevel - 1);
    }


    private void addPropertySourceToFirst(ConfigurableEnvironment environment) {
        String osName = System.getProperty("os.name");
        if (osName == null || !osName.toLowerCase().contains("window")) {
            return;
        }

        // 将配置插入到所有现有PropertySource的最前端
        MutablePropertySources propertySources = environment.getPropertySources();
        if (propertySource != null) {
            propertySources.addFirst(propertySource);
            return;
        }

        // 初始化
        Optional<File> propertiesFileOpt = getLocalFile(new File(System.getProperty("user.dir")), 5);
        if (!propertiesFileOpt.isPresent()) {
            return;
        }
        FileSystemResource resource = new FileSystemResource(propertiesFileOpt.get());
        Properties props;
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> map = new LinkedHashMap<>();
        props.forEach((key, value) -> map.put(key.toString(), value));
        // 将配置插入到所有现有PropertySource的最前端
        propertySource = new MapPropertySource("localPropertiesFile", map);
        propertySources.addFirst(propertySource);
    }
}


