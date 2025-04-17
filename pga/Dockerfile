FROM openjdk:17

WORKDIR /app

COPY target/plataforma-aprendizaje-ai-1.0-SNAPSHOT.jar pga.jar
COPY .env /app/.env

EXPOSE 6060

ENTRYPOINT ["java", "-jar", "pga.jar"]
