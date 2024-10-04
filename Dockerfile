FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.8.1-jdk-slim
COPY --from=build /target/NEWSAMS-1.0-SNAPSHOT.war NEWSAMS.
EXPOSE 8080
ENTRYPOINT ["java","-war","NEWSAMS.war"]