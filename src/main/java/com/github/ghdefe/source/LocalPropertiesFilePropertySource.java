package com.github.ghdefe.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author ggdefe
 * @Date 2025/3/12 15:40
 */
public class LocalPropertiesFilePropertySource extends MapPropertySource {

    private static final Logger log = LoggerFactory.getLogger(LocalPropertiesFilePropertySource.class);

    public LocalPropertiesFilePropertySource(File file) {
        super("LocalPropertiesFile on ProjectRoot:[" + file.getAbsolutePath() + "]", file2map(file));
    }

    private static Map<String, Object> file2map(File file) {
        FileSystemResource resource = new FileSystemResource(file);
        Properties props;
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> map = new LinkedHashMap<>();
        props.forEach((key, value) -> map.put(key.toString(), value));
        if (log.isTraceEnabled()) {
            log.trace("load file[{}] properties: {}", file.getAbsolutePath(), map);
        }
        return map;
    }

    @Override
    public Object getProperty(String name) {
        return super.getProperty(name);
    }

    @Override
    public boolean containsProperty(String name) {
        return super.containsProperty(name);
    }
}
