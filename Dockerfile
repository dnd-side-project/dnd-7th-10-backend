FROM openjdk:11-jdk-slim
WORKDIR /usr/springboot
COPY build/libs/linkApp-0.0.1-SNAPSHOT.jar /usr/springboot/app.jar
ENTRYPOINT ["java", "-jar", "/usr/springboot/app.jar"]
