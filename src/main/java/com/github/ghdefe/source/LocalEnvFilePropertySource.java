package com.github.ghdefe.source;

import org.springframework.core.env.SystemEnvironmentPropertySource;
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
public class LocalEnvFilePropertySource extends SystemEnvironmentPropertySource {
    public LocalEnvFilePropertySource(File file) {
        super("LocalEnvFile on ProjectRoot:[" + file.getAbsolutePath() + "]", file2map(file));
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
        return map;
    }
}
