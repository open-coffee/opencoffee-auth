# CoffeeNet Auth Server

The CoffeeNet Auth Server is an OAuth2 provider to achieve single sign-on for the CoffeeNet. 
It authenticates against a LDAP-Server. See [how to configure LDAP](#LDAP)
To use the Auth-Server in your application, see [README of CoffeeNet Starter Security](https://gitlab.synyx.de/coffeenet/coffeenet-starter/tree/master/coffeenet-starter-sso).

## Endpoints
* User endpoint: `/user` (Endpoint to retrieve user details)
* Authorization endpoint: `/oauth/authorize` (User authorizes client to access the user endpoint)
* Token edpoint: `/oauth/token` (Used to get an access token. See [access token](#access-token))

## Create a new OAuth2 Client

A user with the role `COFFEENET-ADMIN` is able to create a new client at `http(s)://$host/clients/new`.

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

Response looks like this:

        {
          "access_token": "6b311332-f57c-3ee2-a668-57c1db083a5p",
          "token_type": "bearer",
          "expires_in": 42837,
          "scope": "openid"
        }
#### Client Credentials Grant

This grant type is typically used by systems. They request their access token
from the Auth-Server and use it as authentication information for requests to
any CoffeeNet App.

![Client_Credentials Grant_Type](docs/client_credentials_grant_type.jpg)

1. System requests an access token. Request contains:
    * ClientId + ClientSecret as basic auth header:
        `Authorization: basic base64(ClientId:ClientSecret)`
    * Grant type and scope in the body. Looks like the following as form-data


        grant_type = client_credentials
        scope      = openid

2. Auth-Server provides access token if clientId and clientSecret are correct.

Response looks like this:

        {
          "access_token": "6b311332-f57c-3ee2-a668-57c1db083a5p",
          "token_type": "bearer",
          "expires_in": 43189,
          "scope": "openid"
        }
        
## Configuration

### Developer Mode

The Auth-Server can be started in developer mode by setting the property `auth.development` to `true`.
By doing this, a default client is created during startup. 
This client can be used to test your application with integration into the Auth-Server.

Details of the default client:

```
clientId: coffeeNetClient
clientSecret: coffeeNetClientSecret
```

### LDAP
To configure the connection to your LDAP-Server just set the following properties inside your `application.yml`.
This example shows the properties to connect with the LDAP-Server provided by our docker container.

    auth:
      ldap:
        url: ldap://localhost:38900
        base: dc=synyx,dc=coffee
        userSearchBase: ou=People
        userSearchFilter: uid={0}
        groupSearchBase: ou=Groups
        groupSearchFilter: member={0}

### Database

Specify the following properties inside your `application.yml`. 
The following example is configures the application to use the mysql database provided by our docker container.

```
spring.datasource.url=jdbc:mysql://localhost:3306/${Database}
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.datasource.username=${username}
spring.datasource.password=${Password}
spring.datasource.data=${PathTo-data.sql}
```

### Logging

Logging is provided by [CoffeeNet Logging](https://gitlab.synyx.de/coffeenet/coffeenet-starter/tree/master/coffeenet-starter-logging) and can be configured by setting
properties below `coffeenet.logging.*` inside your `application.yml`.

### Service Discovery

The Auth-Server uses [Coffeenet Service Discovery](https://gitlab.synyx.de/coffeenet/coffeenet-starter/tree/master/coffeenet-starter-discovery) and is only visible to users with the role `COFFEENET-ADMIN`.  