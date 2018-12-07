FROM openjdk:8-jre-alpine

EXPOSE 9999

RUN apk add --no-cache curl

ENV JAVA_APP_JAR="app.jar"

VOLUME /tmp
ADD /maven/${project.build.finalName}.jar app.jar
ADD target/docker-extra/run-java/run-java.sh run-java.sh

ENTRYPOINT [ "sh", "-c", "./run-java.sh" ]