FROM eclipse-temurin:21-jre

WORKDIR /app

COPY build/libs/*SNAPSHOT.jar software1cliente.jar

EXPOSE 9001

ENTRYPOINT ["java", "-jar", "software1cliente.jar"]