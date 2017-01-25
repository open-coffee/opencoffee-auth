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

Das Logging wird mit dem CoffeeNet Starter Logging umgesetzt und kann mittels `coffeenet.logging.*` konfiguriert werden.


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

Der Auth-Server kann im Entwicklermodus gestartet werden indem die property `auth.development` auf `true` gesetzt wird.
Dadurch wird beim starten des Auth-Servers ein Client angelegt, der verwendet werden kann, um eine Anwendung lokal integrativ zu testen.
Der Client hat folgende Zugangsdaten:

```
clientId: coffeeNetClient
clientSecret: coffeeNetClientSecret
```

Damit eine Anwendung den Auth-Server nun zur Authentifizierung verwenden kann, muss die Anwendung [entsprechend Konfiguriert werden](https://gitlab.synyx.de/coffeenet/coffeenet-starter-sso#verbindungsinformationen).

## Access Token

### What an access token is for

The access token in CoffeeNet is used to retrieve user/clientdetails from the Auth-server.

![Access_Token_Usage](docs/access_token_usage.jpg)

1. System requests content of a CoffeeNet App with an access token.
    (Header: `Authorzitation: Bearer $accessToken`)

2. CoffeeNet App uses access token to retrieve client details from Auth-Server

3. Auth-Server resolves access token to client details and provides them to the CoffeeNet App

4. CoffeeNet App provides content based on the returned client details

### How to get an access token

There are multiple ways to obtain an access token. In all of them the
 Resource Owner(User) has to grant the application the access to this token.

The CoffeeNet Auth-Server provides the following grant types:
* Authorization Code Grant (Used by web apps i.e. most CoffeeNet Apps)
* Password Grant
* Client Credentials Grant

#### Authorization Code Grant

The most common used grant type is the authorization code grant 
type, since it's the flow that is used for web apps that redirect the 
user to the authorization server to obtain the token.
This flow is the flow you see for example when you visit the coffeenet 
frontpage.

![Authorization Code Web Flow](docs/authorization_code_web_flow.jpg)

1. User navigates to CoffeeNet App 
2. CoffeeNet App checks if session is available for this user 
    * YES: continue with step 13
    * NO: continue with step 3
3. CoffeeNet App sends redirect to Auth-Server (client-id, redirect uri 
in parameters)
4. Auth-Server checks if session is available for this user
    * YES: continue with step 7
    * NO: continue with step 5
5. Auth-Server asks User to enter credentials (Login Form)
6. User enters credentials
7. Auth-Server sends redirect back to redirect uri (should be the address
        of the CoffeeNet App) containing an authorization code in parameters. 
        Users won't event notice this step, if a on auth-server session 
        already exists.
8. CoffeeNetApp requests token with the given authorization code and its
        client credentials (meaning client-id and client-secret).
9. Passes the access and refresh token back to the CoffeeNet App
10. CoffeeNet App requests user details from Auth-Server with the obtained 
        access token.
11. Auth-Server passes user details back to the CoffeeNet App
12. CoffeeNet App Stores user details in a session
13. CoffeeNet App provides content based on the user details

#### Password Grant

This grant type is typically used by developers for testing purpose or systems.

![Password Grant_Type](docs/password_grant_type.jpg)

1. System or Developer requests an access token. Request contains:
    * ClientId + ClientSecret as basic auth header:
        `Authorization: basic base64(ClientId:ClientSecret)`
    * Grant type, username, password and scope in the body. Looks like the
    following as form-data


        grant_type = password
        username   = $username
        password   = $password
        scope      = openid

2. Auth-Server provides access token if username, password, clientId and
clientSecret are correct.

#### Client Credentials Grant

This grant type is typically used by systems. They request their access token
from the Auth-Server and use it as authentication information for requests to
any CoffeeNet App.

![Client_Credentials Grant_Type](docs/client_credentials_grant_type.jpg)

1. System requests an access token. Request contains:
    * ClientId + ClientSecret as basic auth header:
        `Authorization: basic base64(ClientId:ClientSecret)`
    * Grant type and scope in the body. Looks like the following as form-data


        grant_type = password
        scope      = openid

2. Auth-Server provides access token if clientId and clientSecret are correct.