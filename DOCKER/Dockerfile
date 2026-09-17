FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo (opcional pero recomendado)
WORKDIR /

# Copiar JAR
COPY app.jar /app.jar

# Copiar properties del perfil docker
COPY yaml/application-prod.yaml /application-prod.yaml

# Ejecutar app con perfil docker
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=prod","--spring.config.additional-location=file:/"]