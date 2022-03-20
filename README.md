# spring-native-demo

`Spring Native`可以通过`GraalVM`将`SpringBoot`应用程序编译成原生镜像，本地编译的话需要的前置条件比较多，会遇到各种各样的坑，而且`GraalVM`现在还不支持 mac 的 m1 芯片。

为了舒适的体验`Spring Native`，本示例使用了`GitHub Actions`自动构建 docker 镜像，并推送到 docker hub 上，直接使用如下命令：

```
docker run -d -p 8080:8080 --name native-demo \
-v /opt/docker/nativedemo/logs:/workspace/logs \
-v /usr/share/zoneinfo/Asia/Shanghai:/etc/localtime \
dudiao/native-demo:0.0.1-SNAPSHOT
```

- 用户接口：`http://ip:8080/user/list`
- 用户页面：`http://ip:8080/user`
- 新增用户：`POST http://ip:8080/user/add`