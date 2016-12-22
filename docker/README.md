# Docker

With the provided docker-compose file you can test the auth server with its needed services very easy.
When you run the docker-compose.yml file as describes below these services will be started:
* CoffeeNet Auth
* Coffeenet Discovery
* Ldap Server (ds-389)

You will have a fully integrated CoffeeNet environment with the following users:

| User | Username | Password | Roles |
|---|---|---|---|
| Coffy | coffy | coffy | ROLE_COFFEENET-ADMIN & ROLE_USER |
| Liam Spencer | spencer | spencer | ROLE_COFFEENET-ADMIN |
| Logan Gonzalez | gonzalez | gonzalez | ROLE_USER |

and 2000 users for load tests e.g. with the credentials user{1..2000}/user{1..2000} 
(e.g. user1/user1, user2/user2,...) without a role defined.


# Usage with docker-compose

Build the image:

```bash
docker-compose build
```

Start all images

```bash
docker-compose up
```

# LDAP browser/editor

## Apache Directory Manager

### macOS

Install Apache Directory Manager (for example with Homebrew)
```bash
brew install Caskroom/cask/apache-directory-studio
```

### Linux

Download and start the Apache Directory Manager from http://directory.apache.org/studio/download/download-linux.html
