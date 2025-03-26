package com.github.ghdefe.register;

import com.github.ghdefe.util.PropertySourcesOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * hook {@link com.ctrip.framework.apollo.spring.boot.ApolloApplicationContextInitializer}
 */
public class LocalPropertiesEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private static final Logger log = LoggerFactory.getLogger(LocalPropertiesEnvironmentPostProcessor.class);


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.info("postProcessEnvironment add local propertySource");
        PropertySourcesOperator.addPropertySourceToFirst(environment);
    }


    @Override
    public int getOrder() {
        int order = -1;
        log.trace("LocalPropertiesEnvironmentPostProcessor getOrder: {}", order);
        return order;
    }
}


