FROM openjdk:11-jdk
WORKDIR /usr/springboot
COPY linkApp-0.0.1-SNAPSHOT.jar /usr/springboot/app.jar
ENTRYPOINT ["java", "-jar", "/usr/springboot/app.jar"]

