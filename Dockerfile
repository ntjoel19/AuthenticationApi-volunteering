FROM openjdk:17
LABEL authors="Jean Joel NTEPP"

WORKDIR /AuthenticationApiApp
COPY . .
RUN ./mvnw clean package -DskipTests
ENTRYPOINT ["java", "-jar", "target/AuthenticationApi-1.0.1.jar"]