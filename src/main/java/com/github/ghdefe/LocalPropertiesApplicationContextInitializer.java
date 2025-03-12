package com.github.ghdefe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

/**
 * hook {@link com.ctrip.framework.apollo.spring.boot.ApolloApplicationContextInitializer}
 */
public class LocalPropertiesApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, EnvironmentPostProcessor, Ordered {


    private PropertySource propertySource;

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        PropertySourcesOperator.addPropertySourceToFirst(environment);
    }


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        PropertySourcesOperator.addPropertySourceToFirst(applicationContext.getEnvironment());
    }

    @Override
    public int getOrder() {
        return 1;
    }
}


