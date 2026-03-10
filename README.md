# Beelapay Backend (Java 25 + Gradle + Spring Boot)

This project is set up with:
- Java 25 toolchain
- Gradle wrapper (`gradlew`, `gradlew.bat`)
- Spring Boot 4
- Starter dependencies: Web MVC, Validation, Actuator

## Prerequisites
- Java 25 installed and available on `PATH`

## Run Locally
Windows:
```powershell
.\gradlew.bat bootRun
```

macOS/Linux:
```bash
./gradlew bootRun
```

## Quick Health Check
After startup:
- App URL: `http://localhost:8080`
- Ping endpoint: `GET /api/ping`

Example:
```bash
curl http://localhost:8080/api/ping
```

Expected response shape:
```json
{
  "status": "ok",
  "service": "beelapay",
  "timestamp": "2026-03-10T...Z"
}
```

## Build and Test
Windows:
```powershell
.\gradlew.bat build
```

macOS/Linux:
```bash
./gradlew build
```
