FROM openjdk:11-jre-slim
COPY ./build/libs/*.jar api-gateway.jar
ENTRYPOINT ["java", "-Xmx200m", "-jar", "/api-gateway.jar"]