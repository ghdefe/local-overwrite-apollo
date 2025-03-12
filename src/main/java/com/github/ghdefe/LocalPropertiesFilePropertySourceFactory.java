package com.github.ghdefe;

import org.springframework.core.env.PropertySource;
import org.springframework.lang.Nullable;

import java.io.File;
import java.util.Optional;

/**
 * @Author cmz.zeng
 * @Date 2025/3/12 15:40
 */
public class LocalPropertiesFilePropertySourceFactory {

    private static PropertySource<?> propertySource;
    private static final Boolean isInited = false;

    @Nullable
    public static synchronized PropertySource<?> getPropertySource() {
        if (isInited) {
            return propertySource;
        }
        // 初始化
        Optional<File> propertiesFileOpt = getLocalFile(new File(System.getProperty("user.dir")), 5);
        propertySource = propertiesFileOpt.map(LocalPropertiesFilePropertySource::new).orElse(null);
        return propertySource;
    }

    private static Optional<File> getLocalFile(File dir, int searchLevel) {
        // 读取根目录下的local.env文件
        File file = new File(dir, ".env/local.properties");
        if (file.exists()) {
            return Optional.of(file);
        }
        if (searchLevel <= 0) {
            return Optional.empty();
        }
        // 往上一级找
        File parentDir = file.getParentFile();
        return getLocalFile(parentDir, searchLevel - 1);
    }
}
