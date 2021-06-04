# Build stage
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:9

RUN mkdir /opt/appi-match
RUN mkdir /opt/appi-match/config
COPY --from=build /home/app/target/Matcher-0.0.1-SNAPSHOT.jar /opt/appi-match/Matcher.jar

ENTRYPOINT ["java", "-jar", "/opt/appi-match/Matcher.jar"]
