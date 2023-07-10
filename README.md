# WebEditor

## Installation

### Clone WebEditor repository
1. In the directory where you want to clone the project, run:

    ``git clone https://github.com/NaumovichID/WebEditor``
2. Change the current working directory:

    ``cd WebEditor``

### Run WebEditor with Docker:
1. Build a docker image named ``spring-app`` with a following command, note that dot ``.`` is a part of the command:

   ```docker build -t spring-app .```

2. Run a docker container named ``spring-app-container`` based on the image:
   
    ``docker run -it -p 8080:8080 --rm --name spring-app-container spring-app``

    Note: the ``-it`` instructs Docker to allocate a pseudo-TTY connected to the container’s stdin; creating an interactive bash shell in the container.
   
    To detach from a running container: hold ``ctrl`` + press ``p`` + press ``q``.
3. To stop the container, run:

    ``docker stop spring-app-container``
