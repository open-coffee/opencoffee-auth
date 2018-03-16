# Changelog 

### 1.14.0-SNAPSHOT

### 1.13.0

* Change
  * Minimize login logo for 992px-1200px

* Upgrade
  * CoffeeNet starter-logging to version 0.28.0
  * CoffeeNet starter-discovery to version 0.28.0
  * docker-maven-plugin to version 0.24.0

### 1.12.0

* Change
  * New CoffeeNet login page

* Fix
  * coffeenet.discovery.enabled=false does not instantiate 'CoffeeNetAppService'
    that was not optional by the 'LoginController' and not can be optional
  * Use default TlsDirContextAuthenticationStrategy
  * Cleanups

* Add
  * Run integration tests with mariaDB instead of h2
  * More configuration possibilities for clients

* Upgrade
  * CoffeeNet starter-logging to version 0.27.1
  * CoffeeNet starter-discovery to version 0.27.1

### 1.11.1

* Fix
  * Default values for AuthClientDto

### 1.11.0

* Fix
  * Use ldap uid as username instead of user typed username
  * close unnecessary open /health endpoint
  * wrong message property in clients edit page

* Upgrade
  * CoffeeNet starter-logging to version 0.26.0
  * CoffeeNet starter-discovery to version 0.26.0

* Improvement
  * DockerFile - use default alpine openjdk image as base
  * CoffeeNet OAuth2 script to receive token

### 1.10.1
* Fix bug that user endpoint does not return authorities

### 1.10.0
* Upgrade CoffeeNet starter-logging to version 0.23.0
* Upgrade CoffeeNet starter-discovery to version 0.23.0
* Upgrade to Spring Cloud Dalston.SR1
* Change the intern package structure
* Add JWT token store instead of jdbc token store

### 1.9.0
* Upgrade to Spring Boot 1.5.4
* Upgrade to Spring Cloud Camden.SR7
* Upgrade CoffeeNet starter-logging to version 0.21.0
* Upgrade CoffeeNet starter-discovery to version 0.21.0
* Use MariaDB instead of MySQL
* Add new CoffeeNet logo and design

### 1.8.0
* Expose email attribute on '/user' endpoint

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