package com.github.ghdefe;

import org.springframework.core.env.PropertySource;
import org.springframework.lang.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author ggdefe
 * @Date 2025/3/12 15:40
 */
public class LocalPropertiesPropertySourceFactory {

    private static List<PropertySource<?>> propertySources;
    private static Boolean isInited = false;

    @Nullable
    public static synchronized List<PropertySource<?>> getPropertySource() {
        if (isInited) {
            return propertySources;
        }
        propertySources = new ArrayList<>(4);
        // 初始化
        File userDir = new File(System.getProperty("user.dir"));

        getLocalFile(userDir, 5, ".env/local.properties")
                .map(LocalPropertiesFilePropertySource::new)
                .ifPresent(propertySources::add);


        getLocalFile(userDir, 5, ".env/local.env")
                .map(LocalEnvFilePropertySource::new)
                .ifPresent(propertySources::add);



        isInited = true;
        return propertySources;
    }

    private static Optional<File> getLocalFile(File dir, int searchLevel, String filename) {
        // 读取根目录下的local.env文件
        File file = new File(dir, filename);
        if (file.exists()) {
            return Optional.of(file);
        }
        if (searchLevel <= 0) {
            return Optional.empty();
        }
        // 往上一级找
        File parentDir = file.getParentFile();
        return getLocalFile(parentDir, searchLevel - 1, filename);
    }
}
