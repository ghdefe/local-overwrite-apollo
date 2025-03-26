package com.github.ghdefe.register;

import com.github.ghdefe.util.PropertySourcesOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * hook {@link com.ctrip.framework.apollo.spring.boot.ApolloApplicationContextInitializer}
 */
public class LocalPropertiesApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, EnvironmentPostProcessor, Ordered {

    private static final Logger log = LoggerFactory.getLogger(LocalPropertiesApplicationContextInitializer.class);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.info("postProcessEnvironment add local propertySource");
        PropertySourcesOperator.addPropertySourceToFirst(environment);
    }


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("initialize add local propertySource");
        PropertySourcesOperator.addPropertySourceToFirst(applicationContext.getEnvironment());
    }

    @Override
    public int getOrder() {
        int order = 1;
        log.trace("LocalPropertiesApplicationContextInitializer getOrder: {}", order);
        return order;
    }
}


