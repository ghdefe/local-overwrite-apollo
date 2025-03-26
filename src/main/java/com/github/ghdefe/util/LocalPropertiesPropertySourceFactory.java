package com.github.ghdefe.util;

import com.github.ghdefe.source.LocalEnvFilePropertySource;
import com.github.ghdefe.source.LocalFileComposePropertySource;
import com.github.ghdefe.source.LocalPropertiesFilePropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.PropertySource;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author ggdefe
 * @Date 2025/3/12 15:40
 */
public class LocalPropertiesPropertySourceFactory {

    private static final Logger log = LoggerFactory.getLogger(LocalPropertiesPropertySourceFactory.class);

    private static List<PropertySource<?>> propertySources;
    private static Boolean isInited = false;

    @Nullable
    public static synchronized List<PropertySource<?>> getPropertySource() {
        if (isInited) {
            return propertySources;
        }
        log.debug("init local propertySources");
        propertySources = new ArrayList<>(1);

        // 初始化
        LocalFileComposePropertySource composePropertySource = new LocalFileComposePropertySource();
        propertySources.add(composePropertySource);

        File userDir = new File(System.getProperty("user.dir"));

        log.debug("add env file");
        getLocalFile(userDir, 5, ".env", "env")
                .stream()
                .map(LocalEnvFilePropertySource::new)
                .forEach(composePropertySource::addDelegatePropertySource);

        log.debug("add properties file");
        getLocalFile(userDir, 5, ".env", "properties")
                .stream()
                .map(LocalPropertiesFilePropertySource::new)
                .forEach(composePropertySource::addDelegatePropertySource);

        isInited = true;
        return propertySources;
    }

    private static List<File> getLocalFile(File dir, int searchLevel, String subDirName, String extensionName) {
        if (dir == null) {
            return Collections.emptyList();
        }
        // 读取根目录下的local.env文件
        File subDir = new File(dir, subDirName);
        if (!subDir.exists()) {
            if (searchLevel < 0) {
                return Collections.emptyList();
            } else {
                // 递归查上一级文件夹
                return getLocalFile(dir.getParentFile(), searchLevel - 1, subDirName, extensionName);
            }
        }

        log.info("Find Local `.env` Path in {}", subDir.getAbsolutePath());
        try (Stream<Path> walk = Files.walk(subDir.toPath())) {
            List<File> fileList = walk.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith("." + extensionName))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            log.debug("Find {} file: {}", extensionName, fileList);
            return fileList;
        } catch (IOException e) {
            log.error("Error when get LocalFile `{}` in {}: {}", extensionName, subDir, e.getMessage());
            return Collections.emptyList();
        }
    }
}
