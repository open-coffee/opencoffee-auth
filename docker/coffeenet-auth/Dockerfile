FROM java:8

RUN echo "machine nexus.synyx.de login autodeploy-readonly password z9i1s0m5o2" > /root/.netrc
RUN curl  -n -sS -L "https://nexus.synyx.de/service/local/artifact/maven/redirect?g=coffee.synyx&a=auth&v=LATEST&r=snapshots&p=jar" > /auth.jar

EXPOSE 9999

CMD ["java", "-jar", "/auth.jar"]