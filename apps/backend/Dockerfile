FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY gradlew gradlew.bat ./
COPY gradle ./gradle
COPY apps/backend/build.gradle apps/backend/settings.gradle ./
RUN ./gradlew --no-daemon dependencies || true

COPY apps/backend/src ./src

EXPOSE 8080
CMD ["./gradlew", "--no-daemon", "bootRun"]
