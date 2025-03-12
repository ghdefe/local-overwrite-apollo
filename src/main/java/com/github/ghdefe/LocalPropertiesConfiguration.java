package com.github.ghdefe;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(LocalPropertiesBeanFacatoryPostProcessor.class)
public class LocalPropertiesConfiguration {
}
