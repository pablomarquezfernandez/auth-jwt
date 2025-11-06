# Use una imagen base que tenga Java instalado
FROM eclipse-temurin:21-jdk
# FROM tomcat:9.0

# Copia el código fuente de la aplicación a la imagen
COPY . /app

# Establece el directorio de trabajo
WORKDIR /app

# Construye el archivo JAR de la aplicación
RUN ./mvnw clean install -DskipTests

# Ejecuta la aplicación Spring Boot cuando se inicia el contenedor
CMD ["java", "-jar", "target/auth-0.0.1-SNAPSHOT.jar"]

# CMD ["startup.sh"]

EXPOSE 8080