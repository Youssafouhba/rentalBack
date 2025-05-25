# Étape 1: Build de l'application avec Maven
FROM maven:3.8-openjdk-17-slim AS build
WORKDIR /app

# Copier d'abord le fichier pom.xml pour tirer parti du cache des dépendances
COPY pom.xml .
# Télécharger toutes les dépendances
RUN mvn dependency:go-offline -B

# Copier le code source et construire l'application
COPY src ./src
RUN mvn package -DskipTests

# Étape 2: Créer l'image finale avec seulement le JRE et le JAR
FROM eclipse-temurin:17-jre-alpine

# Installer les outils essentiels et configurer les variables d'environnement
RUN apk add --no-cache tzdata curl && \
    cp /usr/share/zoneinfo/Europe/Paris /etc/localtime && \
    echo "Europe/Paris" > /etc/timezone

WORKDIR /app

# Créer un utilisateur non-root pour une sécurité accrue
RUN addgroup --system appgroup && \
    adduser --system appuser --ingroup appgroup

# Copier seulement le JAR depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar
RUN chown -R appuser:appgroup /app

# Utiliser l'utilisateur non-root
USER appuser

# Configuration pour Railway
ENV PORT=8080
EXPOSE ${PORT}

# Configuration JVM optimisée pour les conteneurs
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

# Script de démarrage avec détection de port dynamique pour Railway
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -Dserver.port=${PORT} -jar app.jar"]