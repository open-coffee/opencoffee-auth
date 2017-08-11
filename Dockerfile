FROM openjdk:8-jre-alpine

EXPOSE 9999

VOLUME /tmp
ADD /maven/${docker.finalName}.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]