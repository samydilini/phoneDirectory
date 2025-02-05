FROM openjdk:23
ARG JAR_FILE=target/*.jar
COPY build/libs/phoneDirectory-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]
