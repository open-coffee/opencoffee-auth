FROM debian:jessie
MAINTAINER synyx

ENV DS_PASSWORD Iexeemah4ieb4M
ENV DS_BASE dc=synyx,dc=coffee

RUN apt-get update \
     && apt-get install -y 389-ds \
     && rm -rf /var/lib/apt/lists/*

ADD ds-setup.inf \
    coffeenet-schema.ldif \
    coffeenet-data-people.ldif \
    coffeenet-data-auto-generated-people.ldif \
    coffeenet-data-roles.ldif \
    start.sh \
    /tmp/

RUN chmod +x /tmp/start.sh

EXPOSE 389

CMD /tmp/start.sh
