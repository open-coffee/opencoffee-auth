# Changelog 

### 1.7.1
* Fix logout page was not displayed correctly
* Add developer feature to manage application.properties on project root level that is not tracked by git

### 1.7.0
* Add navigation bar for /clients
* Upgrade to Spring Boot 1.5.1
* Upgrade CoffeeNet starter-logging to version 0.18.0
* Upgrade CoffeeNet starter-discovery to version 0.18.0

### 1.6.0
* Remove refresh token functionality
* Upgrade to Spring Boot 1.4.4
* Upgrade CoffeeNet starter-logging to version 0.15.1
* Upgrade CoffeeNet starter-discovery to version 0.15.1

### 1.5.0
* Add password recovery link
  * Will only provided if the service with a password recovery is available
  * `auth.passwordRecoveryServiceName` and `auth.passwordRecoveryPath`
* Add possibility to configure the connection with or without tls between the auth server and ldap
  * `auth.ldap.connection-with-tls (default: true)`

### 1.4.0
* Remove synyx specific default values from `auth.ldap.*`
* Upgrade CoffeeNet starter-logging to version 0.13.0
* Upgrade CoffeeNet starter-discovery to version 0.13.0

### 1.3.1
* Fix that only in `auth.development=true` mode the test CoffeeNetClient is added

### 1.3.0
* Add CoffeeNet logging
* Add service discovery for `/clients`
* Upgrade latest dependencies
  * Bootstrap 3.3.7-1
  * Font Awesome 4.7.0
* Split configuration properties and development config for liquibase
* Adapt most configuration to `auth.*`
  * `auth.default-redirect-url` default value is now `http://localhost:8080`
  * `auth.development` default value is now `true`
  * `auth.ldap.*` moved from `ldap.*` now with `auth.*` prefix