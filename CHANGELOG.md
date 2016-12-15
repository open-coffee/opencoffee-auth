# Changelog 

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