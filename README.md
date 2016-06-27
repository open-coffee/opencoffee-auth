# CoffeeNet Auth Server

Der Auth Server ist ein OAuth2 Autorisierungsserver für Single Sign On innerhalb des CoffeeNets.
Die Authentifizierung findet gegen den LDAP statt.

## Datenpersistenz

Wird anhand von Properties unterschieden

### H2

Wird gerne für die lokale Entwicklung verwendet unter http://localhost:8083 ist die Konsole zu erreichen.

```
h2-console.port=8083
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.url=jdbc:h2:mem:tokenStore;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.testOnBorrow=true
spring.datasource.validationQuery=SELECT 1
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

Aktuell muss in der Datenbank ein neuer Eintrag angelegt werden. 
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

```
INSERT INTO oauth_client_details VALUES (
  'client_id',
  null,
  'client_secret',
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