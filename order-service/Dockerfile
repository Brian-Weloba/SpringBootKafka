FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# --- install lib (sibling module) ---
COPY lib/pom.xml lib/pom.xml
RUN --mount=type=cache,target=/root/.m2 mvn -f lib/pom.xml -B -DskipTests dependency:go-offline
COPY lib/src lib/src
RUN --mount=type=cache,target=/root/.m2 mvn -f lib/pom.xml -B -DskipTests install

# --- build this service ---
COPY order-service/pom.xml pom.xml
RUN --mount=type=cache,target=/root/.m2 mvn -B -DskipTests dependency:go-offline
COPY order-service/src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -B -DskipTests package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
