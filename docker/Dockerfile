FROM openjdk:17-jdk-alpine

# 设置工作目录
WORKDIR /app

# 复制应用程序JAR文件到容器中
COPY app.jar /app

# 暴露应用程序端口
EXPOSE 10001

# 启动应用程序
CMD ["java", "-jar", "app.jar"]