FROM java:8

ADD application.yml /application.yml

RUN echo "machine nexus.synyx.de login autodeploy-readonly password z9i1s0m5o2" > /root/.netrc
RUN curl  -n -sS -L "https://nexus.synyx.de/service/local/artifact/maven/redirect?g=coffee.synyx&a=discovery&v=LATEST&r=snapshots&p=jar" > /discovery.jar

EXPOSE 8761

CMD ["java", "-jar", "/discovery.jar"]