FROM openjdk
ARG JAR_FILE=target/ResalePlatform-0.0.1-SNAPSHOT.jar
WORKDIR /app
COPY target/ResalePlatform-0.0.1-SNAPSHOT.jar /app/ResalePlatform-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ResalePlatform-0.0.1-SNAPSHOT.jar"]