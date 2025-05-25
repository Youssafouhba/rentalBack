# Stage 1: Build the backend application
FROM maven:3.9.6-eclipse-temurin-17 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -f pom.xml clean package -Dmaven.test.skip

# Stage 2: Run the backend application
FROM openjdk:17
COPY --from=build /workspace/target/*.jar app.jar
COPY zyn/ssl/localhost/keystore.p12 zyn/ssl/localhost/keystore.p12
COPY zyn/ssl/prod/keystore.p12 zyn/ssl/prod/keystore.p12


EXPOSE 8036
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
