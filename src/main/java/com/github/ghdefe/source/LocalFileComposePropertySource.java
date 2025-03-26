package com.github.ghdefe.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.PropertySource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LocalFileComposePropertySource extends PropertySource<List<PropertySource<?>>> {

    private static final Logger log = LoggerFactory.getLogger(LocalFileComposePropertySource.class);

    private final List<PropertySource<?>> delegatePropertySources;

    public LocalFileComposePropertySource() {
        super("LocalFileComposePropertySource", getPropertySources());
        this.delegatePropertySources = this.getSource();
    }

    private static List<PropertySource<?>> getPropertySources() {
        return new ArrayList<>();
    }

    @Nullable
    @Override
    public Object getProperty(@NonNull String name) {
        for (PropertySource<?> delegatePropertySource : this.delegatePropertySources) {
            Object property = delegatePropertySource.getProperty(name);
            if (property != null) {
                log.trace("Get config: {} from {}", name, delegatePropertySource.getName());
                return property;
            }
        }
        return null;
    }

    public void addDelegatePropertySource(PropertySource<?> delegatePropertySource) {
        this.delegatePropertySources.add(delegatePropertySource);
    }
}
