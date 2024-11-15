# Utilizar una imagen base de OpenJDK 21
FROM openjdk:21-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR generado al contenedor
COPY target/tarea2-0.0.1-SNAPSHOT.jar Tarea2.jar

# Copia el archivo application.properties al contenedor
COPY src/main/resources/application-docker.properties /app/application.properties

# Exponer el puerto en el que tu aplicación escucha (ajusta según sea necesario)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "Tarea2.jar"]