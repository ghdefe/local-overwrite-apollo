package com.github.ghdefe;

import com.github.ghdefe.register.LocalPropertiesBeanFacatoryPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(LocalPropertiesBeanFacatoryPostProcessor.class)
public class LocalPropertiesConfiguration {

}
