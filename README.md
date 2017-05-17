# spring-boot-chat
![Screenshot](/screenshot.png)

## Run with Maven
`mvn spring-boot:run`

## Run with Docker
`mvn clean package docker:build && docker run -p 8080:8080 -d rydogs/spring-boot-chat`