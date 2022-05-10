FROM maven:3.8.5-openjdk-11-slim as builder
WORKDIR /project
COPY /src /project/src
COPY pom.xml /project/
RUN mvn -B package -DskipTests

FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=builder project/target/*.jar /app/InternalService-0.0.1-SNAPSHOT.jar
EXPOSE 8081
CMD ["java", "-jar", "InternalService-0.0.1-SNAPSHOT.jar"]