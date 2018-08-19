# Changelog 
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).


## [0.16.1]
### Changed
- CoffeeNet Starter Parent to version `0.33.1`


## [1.16.0]
### Added
- Add `auth.ldap.bindDn` and `auth.ldap.bindPassword`
- Actuator to have `/health` endpoint

### Changed
- CoffeeNet starter-logging to version 0.32.0
- Changelog format

### Fixed
- Link in readme


## [1.15.0]
### Changed
- CoffeeNet starter-logging to version 0.31.0


## [1.14.0]
### Changed
- Minimize login logo for 1200px and above
- CoffeeNet starter-logging to version 0.29.0


## [1.13.0]
### Changed
- Minimize login logo for 992px-1200px
- Upgrade
  - CoffeeNet starter-logging to version 0.28.0
  - CoffeeNet starter-discovery to version 0.28.0
  - docker-maven-plugin to version 0.24.0


## [1.12.0]
### Added
- Run integration tests with mariaDB instead of h2
- More configuration possibilities for clients

### Changed
- New CoffeeNet login page
- Upgrade
  - CoffeeNet starter-logging to version 0.27.1
  - CoffeeNet starter-discovery to version 0.27.1

### Fixed
- coffeenet.discovery.enabled=false does not instantiate 'CoffeeNetAppService'
  that was not optional by the 'LoginController' and not can be optional
- Use default TlsDirContextAuthenticationStrategy
- Cleanups


## [1.11.1]
### Fixed
- Default values for AuthClientDto


## [1.11.0]
### Fixed
- Use ldap uid as username instead of user typed username
- close unnecessary open /health endpoint
- wrong message property in clients edit page

### Changed
- CoffeeNet starter-logging to version 0.26.0
- CoffeeNet starter-discovery to version 0.26.0
- DockerFile - use default alpine openjdk image as base
- CoffeeNet OAuth2 script to receive token


## [1.10.1]
### Fixed
- Fix bug that user endpoint does not return authorities


## [1.10.0]
### Changed
- Upgrade CoffeeNet starter-logging to version 0.23.0
- Upgrade CoffeeNet starter-discovery to version 0.23.0
- Upgrade to Spring Cloud Dalston.SR1
- Change the intern package structure
- Add JWT token store instead of jdbc token store


## [1.9.0]
### Added
- Add new CoffeeNet logo and design

### Changed
- Upgrade to Spring Boot 1.5.4
- Upgrade to Spring Cloud Camden.SR7
- Upgrade CoffeeNet starter-logging to version 0.21.0
- Upgrade CoffeeNet starter-discovery to version 0.21.0
- Use MariaDB instead of MySQL


## [1.8.0]
### Added
- Expose email attribute on '/user' endpoint


## [1.7.1]
### Added
- Add developer feature to manage application.properties on project root level that is not tracked by git

### Fixed
- Fix logout page was not displayed correctly


## [1.7.0]
### Added
- Add navigation bar for /clients

### Changed
- Upgrade to Spring Boot 1.5.1
- Upgrade CoffeeNet starter-logging to version 0.18.0
- Upgrade CoffeeNet starter-discovery to version 0.18.0


## [1.6.0]
### Changed
- Upgrade to Spring Boot 1.4.4
- Upgrade CoffeeNet starter-logging to version 0.15.1
- Upgrade CoffeeNet starter-discovery to version 0.15.1

### Removed
- Remove refresh token functionality


## [1.5.0]
### Added
- Add password recovery link
  - Will only provided if the service with a password recovery is available
  - `auth.passwordRecoveryServiceName` and `auth.passwordRecoveryPath`
- Add possibility to configure the connection with or without tls between the auth server and ldap
  - `auth.ldap.connection-with-tls (default: true)`


## [1.4.0]
### Changed
- Upgrade CoffeeNet starter-logging to version 0.13.0
- Upgrade CoffeeNet starter-discovery to version 0.13.0

### Removed
- Remove synyx specific default values from `auth.ldap.*`


## [1.3.1]
### Fixed
- Fix that only in `auth.development=true` mode the test CoffeeNetClient is added


## 1.3.0
### Added
- Add CoffeeNet logging
- Add service discovery for `/clients`

### Changed
- Upgrade latest dependencies
  - Bootstrap 3.3.7-1
  - Font Awesome 4.7.0
- Split configuration properties and development config for liquibase
- Adapt most configuration to `auth.*`
  - `auth.default-redirect-url` default value is now `http://localhost:8080`
  - `auth.development` default value is now `true`
  - `auth.ldap.*` moved from `ldap.*` now with `auth.*` prefix


[0.16.1]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.16.0...auth-0.16.1
[1.16.0]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.15.0...auth-1.16.0
[1.15.0]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.14.0...auth-1.15.0
[1.14.0]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.13.0...auth-1.14.0
[1.13.0]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.12.0...auth-1.13.0
[1.12.0]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.11.0...auth-1.12.0
[1.11.0]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.10.1...auth-1.11.0
[1.10.1]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.10...auth-1.10.1
[1.10.0]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.9...auth-1.10
[1.9.0]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.8...auth-1.9
[1.8.0]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.7.1...auth-1.8
[1.7.1]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.7...auth-1.7.1
[1.7.0]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.6...auth-1.7
[1.6.0]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.5...auth-1.6
[1.5.0]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.4...auth-1.5
[1.4.0]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.3.1...auth-1.4
[1.3.1]: https://github.com/coffeenet/coffeenet-auth/compare/auth-1.3...auth-1.3.1
