# SpeakTest Backend - Spring Boot Application

## Project Overview

SpeakTest is a cloud-native language proficiency assessment platform supporting English and French. This backend is built with Spring Boot 4.0.1 and Java 17, designed to support both traditional deployment and future serverless migration.

## Technology Stack

- **Framework**: Spring Boot 4.0.1
- **Java Version**: 17
- **Database**: PostgreSQL (with Flyway migrations)
- **Security**: Spring Security + JWT
- **AWS Integration**: S3, Transcribe, Cognito
- **API Documentation**: SpringDoc OpenAPI 3
- **Build Tool**: Maven
- **ORM**: Hibernate/JPA

## Project Structure

```
src/main/java/hamza/patient/net/speaktest/
├── SpeakTestApplication.java          # Main application class
├── common/                             # Shared components
│   ├── constants/                      # Application constants
│   │   ├── AppConstants.java
│   │   └── ErrorMessages.java
│   ├── dto/                            # Common DTOs
│   │   ├── ApiResponse.java
│   │   ├── ErrorResponse.java
│   │   └── PageResponse.java
│   └── enums/                          # Enumerations
│       ├── CefrLevel.java              # A1-C2 levels
│       ├── Language.java               # EN, FR
│       ├── QuestionType.java
│       ├── TestStatus.java
│       └── UserRole.java               # ADMIN, USER
├── config/                             # Configuration classes (to be created)
│   ├── SecurityConfig.java
│   ├── AwsConfig.java
│   ├── DatabaseConfig.java
│   └── OpenApiConfig.java
├── auth/                               # Authentication module
│   ├── domain/
│   │   └── User.java                   # User entity
│   ├── repository/
│   │   └── UserRepository.java
│   ├── service/                        # (to be created)
│   │   ├── AuthService.java
│   │   ├── UserService.java
│   │   └── JwtService.java
│   ├── controller/                     # (to be created)
│   │   ├── AuthController.java
│   │   └── UserController.java
│   └── dto/
│       ├── LoginRequest.java
│       ├── LoginResponse.java
│       ├── RegisterRequest.java
│       └── UserProfileDto.java
├── test/                               # Test management module (to be created)
│   ├── domain/
│   ├── repository/
│   ├── service/
│   ├── controller/
│   └── dto/
├── audio/                              # Audio processing module (to be created)
│   ├── domain/
│   ├── repository/
│   ├── service/
│   ├── controller/
│   └── dto/
├── scoring/                            # Scoring module (to be created)
│   ├── service/
│   ├── controller/
│   └── dto/
└── exception/                          # Exception handling
    ├── AudioProcessingException.java
    ├── BadRequestException.java
    ├── GlobalExceptionHandler.java
    ├── ResourceNotFoundException.java
    ├── TranscriptionException.java
    └── UnauthorizedException.java

src/main/resources/
├── application.yml                     # Main configuration
├── application-dev.yml                 # Development profile
├── application-prod.yml                # Production profile
└── db/migration/                       # Flyway migrations
    ├── V1__create_users_table.sql
    ├── V2__create_tests_table.sql
    ├── V3__create_questions_table.sql
    └── V4__create_test_attempts_table.sql
```

## Database Schema

### Tables

1. **users** - User accounts and authentication
   - Primary Key: `user_id` (UUID)
   - Unique: `username`, `email`
   - Fields: role, preferred_language, password_hash

2. **tests** - Test metadata
   - Primary Key: `test_id` (UUID)
   - Fields: title, description, language, target_level, duration, question_count

3. **questions** - Test questions
   - Primary Key: `question_id` (UUID)
   - Foreign Key: `test_id` → tests
   - Unique: (test_id, order_num)

4. **test_attempts** - User test attempts and scores
   - Primary Key: `attempt_id` (UUID)
   - Foreign Keys: `user_id` → users, `test_id` → tests
   - Fields: status, scores (fluency, vocabulary, grammar, pronunciation), cefr_level

## Configuration

### Development Profile (`application-dev.yml`)

- PostgreSQL: `localhost:5432/speaktest_dev`
- JWT: Development secret key
- AWS: LocalStack endpoints
- Security: Relaxed for testing
- Logging: DEBUG level

### Production Profile (`application-prod.yml`)

- Database: Environment variables
- JWT: Secure secret from environment
- AWS: Production endpoints
- Security: Full enforcement
- Logging: INFO level

## API Endpoints (Planned)

### Authentication (`/api/v1/auth`)
- `POST /register` - User registration
- `POST /login` - User login
- `POST /refresh` - Refresh JWT token
- `POST /logout` - User logout

### Users (`/api/v1/users`)
- `GET /profile` - Get user profile
- `PUT /profile` - Update profile
- `PUT /password` - Change password

### Tests (`/api/v1/tests`)
- `GET /` - List all tests
- `GET /{id}` - Get test details
- `POST /` - Create test (Admin)
- `PUT /{id}` - Update test (Admin)
- `DELETE /{id}` - Delete test (Admin)
- `GET /{id}/questions` - Get test questions

### Test Attempts (`/api/v1/attempts`)
- `POST /start` - Start test attempt
- `POST /{id}/submit` - Submit attempt
- `GET /{id}` - Get attempt details
- `GET /users/{userId}/attempts` - User history

### Audio (`/api/v1/audio`)
- `POST /upload-url` - Get S3 pre-signed URL
- `POST /process` - Trigger transcription

### Scoring (`/api/v1/scoring`)
- `POST /analyze` - Analyze transcription
- `GET /results/{attemptId}` - Get results

## Getting Started

### Prerequisites

- Java 17
- Maven 3.8+
- PostgreSQL 14+
- (Optional) Docker for LocalStack

### Setup

1. **Clone the repository**
   ```bash
   cd c:\Users\hamza\IdeaProjects\SpeakTest
   ```

2. **Configure PostgreSQL**
   ```sql
   CREATE DATABASE speaktest_dev;
   CREATE USER postgres WITH PASSWORD 'postgres';
   GRANT ALL PRIVILEGES ON DATABASE speaktest_dev TO postgres;
   ```

3. **Update application-dev.yml** (if needed)
   - Database credentials
   - JWT secret
   - AWS configuration

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run database migrations**
   ```bash
   mvn flyway:migrate
   ```

6. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

7. **Access Swagger UI**
   - URL: http://localhost:8080/swagger-ui.html
   - API Docs: http://localhost:8080/api/v1/api-docs

## Development Guidelines

### Code Organization

- **Layered Architecture**: Controller → Service → Repository
- **DTOs**: Separate from entities for API contracts
- **Validation**: Use Jakarta Validation on DTOs
- **Exception Handling**: Centralized in GlobalExceptionHandler
- **Logging**: Use SLF4J with structured logging

### Naming Conventions

- **Entities**: Singular nouns (User, Test, Question)
- **Repositories**: EntityNameRepository
- **Services**: EntityNameService
- **Controllers**: EntityNameController
- **DTOs**: PurposeEntityNameDto (e.g., CreateTestRequest)

### Security

- JWT-based authentication
- Role-based authorization (ADMIN, USER)
- Password hashing with BCrypt
- CORS configuration for Angular frontend

## Next Steps

1. ✅ Project structure defined
2. ✅ Dependencies configured
3. ✅ Database schema created
4. ✅ Common utilities and DTOs
5. ✅ Exception handling
6. ✅ Auth module entities and DTOs
7. ⏳ Configuration classes (Security, AWS, OpenAPI)
8. ⏳ Auth services and controllers
9. ⏳ Test management module
10. ⏳ Audio processing module
11. ⏳ Scoring module
12. ⏳ Integration tests
13. ⏳ API documentation

## Dependencies

Key dependencies included:
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Validation
- PostgreSQL Driver
- Flyway (database migrations)
- AWS SDK (S3, Transcribe, Cognito)
- JWT (jjwt)
- MapStruct (DTO mapping)
- SpringDoc OpenAPI (API documentation)
- Lombok (boilerplate reduction)

## License

Proprietary - SpeakTest Platform

## Contact

For questions or support, contact the development team.
