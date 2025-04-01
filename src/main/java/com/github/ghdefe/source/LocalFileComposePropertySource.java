package com.github.ghdefe.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocalFileComposePropertySource extends EnumerablePropertySource<List<EnumerablePropertySource<?>>> {

    private static final Logger log = LoggerFactory.getLogger(LocalFileComposePropertySource.class);

    private final List<EnumerablePropertySource<?>> delegatePropertySources;

    public LocalFileComposePropertySource() {
        super("LocalFileComposePropertySource", getPropertySources());
        this.delegatePropertySources = this.getSource();
    }

    private static List<EnumerablePropertySource<?>> getPropertySources() {
        return new ArrayList<>();
    }

    @Nullable
    @Override
    public Object getProperty(@NonNull String name) {
        for (EnumerablePropertySource<?> delegatePropertySource : this.delegatePropertySources) {
            Object property = delegatePropertySource.getProperty(name);
            if (property != null) {
                log.trace("Get config: {} from {}", name, delegatePropertySource.getName());
                return property;
            }
        }
        return null;
    }

    public void addDelegatePropertySource(EnumerablePropertySource<?> delegatePropertySource) {
        this.delegatePropertySources.add(delegatePropertySource);
    }

    @Override
    public String[] getPropertyNames() {
        return this.delegatePropertySources.stream()
                .flatMap(enumerablePropertySource -> Arrays.stream(enumerablePropertySource.getPropertyNames()))
                .toArray(String[]::new);
    }
}
