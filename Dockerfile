FROM openjdk:21-jdk-slim

# Define la variable del archivo JAR
ARG JAR_FILE=target/healthy-api-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} healthy-api.jar

# Expone el puerto 8080
EXPOSE 8080

# Comando para ejecutar el archivo JAR
ENTRYPOINT ["java", "-jar", "healthy-api.jar"]