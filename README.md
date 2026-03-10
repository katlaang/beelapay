# Beelapay Backend (Java 25 + Gradle + Spring Boot)

This project is set up with:
- Java 25 toolchain
- Gradle wrapper (`gradlew`, `gradlew.bat`)
- Spring Boot 4.0.3
- Starter dependencies: Web MVC, Validation, Actuator, Security, Data JPA
- PostgreSQL 16 + Flyway migrations

## Repository Layout

```text
beelapay/
|- apps/
|  |- backend/
|  |  |- build.gradle
|  |  |- settings.gradle
|  |  |- Dockerfile
|  |  `- src/
|  |     |- main/
|  |     |  |- java/com/beelapay/
|  |     |  `- resources/
|  |     |     |- application.yml
|  |     |     `- db/migration/
|  |     `- test/
|- infra/
|  `- docker-compose.yml
`- README.md
```

## Prerequisites
- Java 25 installed and available on `PATH`
- Docker (optional, for local Postgres)

## Run Locally

Windows:
```powershell
cd infra
docker compose up -d
cd ..
.\gradlew.bat -p apps/backend bootRun
```

macOS/Linux:
```bash
cd infra
docker compose up -d
cd ..
./gradlew -p apps/backend bootRun
```

## Quick Health Check
After startup:
- App URL: `http://localhost:8080`
- Health endpoint: `GET /api/health`
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

## Implemented APIs (Phase 1 Start)
- `GET /api/health`
- `POST /api/identity/otp/send`
- `POST /api/identity/otp/verify`
- `POST /api/identity/register-master`
- `POST /api/identity/attached`
- `POST /api/wallets`
- `GET /api/wallets/{userId}`
- `POST /api/transfers/internal`

## Build and Test
Windows:
```powershell
.\gradlew.bat -p apps/backend clean test
```

macOS/Linux:
```bash
./gradlew -p apps/backend clean test
```
