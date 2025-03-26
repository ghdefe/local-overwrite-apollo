


# LOCAL-OVERWRITE-APOLLO
**fuck apollo**: 本依赖能够使用本地配置文件配置覆盖apollo配置。搜索项目根目录下的`.env/local.properties`和`.env/local.env`文件加载作为spring启动配置, 其中`.env/local.env`是环境变量配置格式.

## QUICK START

1. 引入maven依赖
    ```xml
            <dependency>
                <groupId>com.github.ghdefe</groupId>
                <artifactId>local-overwrite-apollo</artifactId>
                <version>0.0.4</version>
            </dependency>
    ```
1. 本地项目根目录下创建`.env`文件夹, 然后创建`local.properties`或者`local.env`文件  

1. 从git中排除本地配置文件，避免误提交本地配置到远程
    ```
    echo "/.env/" >> .gitignore
    ```
