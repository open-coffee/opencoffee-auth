# CoffeeNet Auth Server

Der Auth Server ist ein OAuth2 Autorisierungsserver für Single Sign On innerhalb des CoffeeNets.
Die Authentifizierung findet gegen den LDAP statt.

## Datenpersistenz

Es werden zwei verschiedene Persistenztechnologien eingesetzt. Einmal eine die H2 In-Memory Datenbank für die lokale
Entwicklung und im produktiven Betrieb eine MySQL Datenbank.

### H2

Wird für die lokale Entwicklung verwendet und stellt eine [H2-Webkonsole](http://localhost:9999/h2-console/) bereit.

```
spring:
  h2:
    console:
      enabled: true
  datasource:
    url: jdbc:h2:mem:auth;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    driverClassName: org.h2.Driver
    username: username
    password:
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: validate
```

### MYSQL

Wird für den produktiven Betrieb verwenden.

```
spring.datasource.initialize=false
spring.datasource.url=jdbc:mysql://localhost:3306/${Database}
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.datasource.username=${username}
spring.datasource.password=${Password}
spring.datasource.data=${PathTo-data.sql}
```

## Logging

Logging wird mit SLF4J umgesetzt. 
Logrotate wird von Journalctl übernommen.

Eine Logzeile sieht wie folgt aus: 
```
JJJJ-MM-TT- HH:mm:SS INFO|DEBUG|WARN|ERROR $prozessId --- [nio-$port-exec-$thread] $Class: $logText
```

## Neue Anwendung einbinden

Die Anwendung muss folgende OAuth-Pfade bedienen können:

### Autorisierungspfade

* Servlet URL: `https://auth.synyx.coffee/auth`
* Autorisierungspfad: `/oauth/authorize` (Pfad zur initialien Autorisierung)
* Identitätspfad: `/user` (Pfad zum Abrufen der Nutzerinformation)
* Pfad des Token: `/oauth/token` (Liefert AccessToken zurück, abh. vom grant type)

### Neuen Service im Auth Server einpflegen

Als Mitarbeiter mit der Rolle `COFFEENET-ADMIN` ist es möglich unter http(s)://$host/clients/new einen neuen Client anzulegen.
Alternativ kann in der Datenbank ein neuer Eintrag angelegt werden. 
Dabei sind folgende Felder relevant:
 * `client_id`: Die Id des Clients, bspw. Name des Service
 * `resource_ids`: Kann leer bleiben
 * `client_secret`: Ein Geheimnis
 * `scope`: Aktuell wird nur der scope `openid` unterstützt.
 * `authorized_grant_types`: Folgende grant types müssen konfiguriert werden: 'authorization_code,password,refresh_token,client_credentials' (siehe https://tools.ietf.org/html/rfc6749#page-8)
 * `web_server_redirect_uri`: Hier müssen alle möglichen URLs eingetragen werden unter denen der Service erreichbar ist (Komma-separierte Liste)
 * `authorities`: Kann leer bleiben
 * `access_token_validity`: Kann leer bleiben
 * `refresh_token_validity`: Kann leer bleiben
 * `additional_information`: Kann leer bleiben
 * `autoapprove`: true


#### Ein Beispiel

Dieses Beispiel ist als default in allen Applikationen die an das CoffeeNet angeschlossen sind als default konfiguriert.

```
INSERT INTO oauth_client_details VALUES (
  'pseudoClientId',
  null,
  'pseudoClientSecret',
  'openid',
  'authorization_code,password,refresh_token',
  'http://servicename, https://servicename, http://servicename.synyx.coffee, https://servicename.synyx.coffee, http://servicename.synyx.de, https://servicename.synyx.de',
  null,
  null,
  null,
  null,
  'true'
);
```

## Entwicklermodus

Der Auth-Server kann im Entwicklermodus gestartet werden indem die property `coffeenet.development` auf true gesetzt wird.
Dadurch wird beim starten des Auth-Servers ein Client angelegt, der verwendet werden kann, um eine Anwendung lokal integrativ zu testen.
Der Client hat folgende Zugangsdaten:

```
clientId: testClient
clientSecret: testClientSecret
```

Damit eine Anwendung den Auth-Server nun zur Authentifizierung verwenden kann, muss die Anwendung [entsprechend Konfiguriert werden](https://gitlab.synyx.de/coffeenet/coffeenet-starter-sso#verbindungsinformationen).