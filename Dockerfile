FROM openjdk:slim
VOLUME /tmp
ARG JAR_FILE=target/application-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} application.jar
ENTRYPOINT ["java","-jar","application.jar"]