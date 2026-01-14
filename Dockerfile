# 阶段 1: 构建阶段
# 使用 Maven 官方镜像进行编译
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# 将 pom.xml 和源码复制到镜像中
COPY pom.xml .
COPY src ./src

# 执行打包命令，跳过测试以加快速度
RUN mvn clean package -DskipTests

# 阶段 2: 运行阶段
# 使用轻量级的 JRE 镜像
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 从构建阶段复制生成的 JAR 包
# 注意：这里使用了你提供的具体 JAR 包名称
COPY --from=build /app/target/JavaSprint4_2CRUDLevel2Mysql-0.0.1-SNAPSHOT.jar app.jar

# 暴露 Spring Boot 默认端口
EXPOSE 8080

# 配置环境变量默认值（可以在运行容器时被覆盖）
ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/mytest
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=888888

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]