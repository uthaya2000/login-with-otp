FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src/
RUN mvn clean package

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/login-with-otp.jar /app/login-with-otp.jar
EXPOSE 8445
CMD ["java", "-jar", "login-with-otp.jar"]