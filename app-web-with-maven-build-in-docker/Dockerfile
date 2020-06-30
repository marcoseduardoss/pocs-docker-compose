
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true
#RUN mvn -f /home/app/pom.xml clean package 

#
# Package stage poc-sping-docker-0.0.1-SNAPSHOT.jar
#
FROM openjdk:8-jdk-alpine
LABEL maintainer="my_api"
VOLUME /tmp
COPY --from=build /home/app/target/poc-sping-docker-*.jar /usr/local/lib/app-web.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app-web.jar"]
