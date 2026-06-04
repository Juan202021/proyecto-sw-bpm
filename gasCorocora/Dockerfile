FROM eclipse-temurin:17-jre

WORKDIR /app

COPY ./target/*.jar gasCorocora.jar

EXPOSE 9000

ENTRYPOINT ["java","-jar","gasCorocora.jar"]