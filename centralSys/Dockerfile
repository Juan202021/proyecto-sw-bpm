FROM eclipse-temurin:17-jre

WORKDIR /app

COPY ./target/*.jar CentralSys.jar

EXPOSE 9003

ENTRYPOINT ["java", "-jar", "CentralSys.jar"]