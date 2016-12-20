# Docker

First, both images need to be built:

```bash
cd ds-389
docker build -t registry-docker.synyx.coffee/ds-389 .
cd ..
```
The output of *docker images* should now show the ds-389 image

Start a container for the ds-389 image with the following command:

```bash
docker run -ti -p 38900:389 --hostname ldap.synyx.coffee registry-docker.synyx.coffee/ds-389
```

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
