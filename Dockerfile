#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/ITIreland-0.0.1-SNAPSHOT.jar ITIreland.jar
#ENV PORT=8080
EXPOSE 80
ENTRYPOINT ["java","-jar","ITIreland.jar"]