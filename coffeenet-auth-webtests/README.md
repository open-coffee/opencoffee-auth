# CoffeeNet Auth Webtests

Webtests are based on [Selenium Webdriver](http://www.seleniumhq.org/) and are managed by [Serenity](http://thucydides.info).

## Prerequisites

### Docker
We dockerized all applications and services that are needed to run this webtests.
All you need is docker v1.9 or higher.

More information about the selenium container can be found at their [GitHub project](https://github.com/SeleniumHQ/docker-selenium).

# Configuration
Additional configuration can be performed in `serenity.properties`.
Check the Serenity documentation for more details.

# Run Tests
Execute:
```
./mvnw clean verify
```

This will run all tests with the browser specified in the `serenity.properties`.
A report will be generated which is available under `target/site/serenity/index.html`.
