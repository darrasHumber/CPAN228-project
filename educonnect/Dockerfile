# ─────────────────────────────────────────────
#  EduConnect – Dockerfile
#  Multi-stage build:
#  Stage 1: Build the JAR with Maven
#  Stage 2: Run the JAR with a slim JRE
# ─────────────────────────────────────────────

# ── Stage 1: Build ────────────────────────────
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml first — lets Docker cache dependencies
# so they aren't re-downloaded on every code change
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests -q

# ── Stage 2: Run ──────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the built JAR from Stage 1
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run with QA profile (MySQL)
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=qa", "app.jar"]
