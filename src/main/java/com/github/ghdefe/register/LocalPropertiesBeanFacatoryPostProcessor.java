package com.github.ghdefe.register;

import com.github.ghdefe.util.PropertySourcesOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * hook {@link com.ctrip.framework.apollo.spring.config.PropertySourcesProcessor}
 */
public class LocalPropertiesBeanFacatoryPostProcessor implements BeanFactoryPostProcessor, EnvironmentAware, PriorityOrdered {

    private static final Logger log = LoggerFactory.getLogger(LocalPropertiesBeanFacatoryPostProcessor.class);

    private ConfigurableEnvironment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("postProcessBeanFactory add local propertySource");
        PropertySourcesOperator.addPropertySourceToFirst(environment);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }



    @Override
    public int getOrder() {
        int order = Ordered.HIGHEST_PRECEDENCE + 1;
        log.trace("LocalPropertiesBeanFacatoryPostProcessor getOrder: {}", order);
        return order;
    }
}


