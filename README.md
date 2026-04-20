# HealthCareGov — Identity & User Management Backend

A secure, production-ready REST API backend for a government healthcare platform. Built with **Spring Boot 3.2**, **Spring Security + JWT**, **JPA/Hibernate**, and **MySQL**. Handles user registration, authentication, role-based access control, and full audit logging for every action.

---

## Features

- **JWT Authentication** — stateless Bearer token auth with 24-hour expiry
- **Role-Based Access Control** — 6 distinct roles enforced at the security layer
- **User Management** — full CRUD with partial update support
- **Audit Logging** — every login and registration is automatically recorded
- **Input Validation** — request-level validation with descriptive error messages
- **Global Exception Handling** — consistent JSON error responses across all endpoints
- **Actuator** — `/actuator/health` and `/actuator/info` exposed for monitoring
- **BCrypt Password Encoding** — passwords never stored in plain text

---

## Roles

| Role | Description |
|---|---|
| `PATIENT` | End user accessing personal health services |
| `DOCTOR` | Medical professional managing patient records |
| `HOSPITAL_ADMIN` | Administrator managing hospital-level operations |
| `PROGRAM_MANAGER` | Oversees healthcare programs and workflows |
| `COMPLIANCE_OFFICER` | Monitors regulatory compliance |
| `GOVERNMENT_AUDITOR` | Audits platform activity and data integrity |

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.2.4 |
| Security | Spring Security + JJWT 0.12.5 |
| Persistence | Spring Data JPA + Hibernate |
| Database | MySQL 8 |
| Boilerplate | Lombok |
| Validation | Jakarta Bean Validation |
| Build | Maven |

---

## Getting Started

### Prerequisites
- Java 21+
- Maven 3.8+
- MySQL 8 running on `localhost:3306`

### Run

```bash
# Clone and navigate
cd healthcaregov-backend

# Configure DB credentials in
# src/main/resources/application.properties

# Build and run
mvn spring-boot:run
```

Server starts on **`http://localhost:8081`**

The database `healthcaregov_identity` is created automatically on first run.

---

## API Reference

Base URL: `http://localhost:8081`

All protected endpoints require the header:
```
Authorization: Bearer <token>
```

---

## Auth Endpoints — `/api/v1/auth`

### POST `/api/v1/auth/register`
Register a new user. Public endpoint — no token required.

**Request**
```json
{
  "name": "Jane Doe",
  "role": "PATIENT",
  "email": "jane.doe@example.com",
  "phone": "9876543210",
  "password": "SecurePass@123"
}
```

**Response — 201 Created**
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "userId": 1,
    "name": "Jane Doe",
    "role": "PATIENT",
    "email": "jane.doe@example.com",
    "phone": "9876543210",
    "status": "ACTIVE",
    "createdAt": "2024-06-01T10:00:00"
  }
}
```

**Validation Rules**
- `name` — required
- `role` — required, must be one of the 6 valid roles
- `email` — required, must be a valid email format
- `password` — required, minimum 8 characters

---

### POST `/api/v1/auth/login`
Authenticate and receive a JWT token. Public endpoint — no token required.

**Request**
```json
{
  "email": "jane.doe@example.com",
  "password": "SecurePass@123"
}
```

**Response — 200 OK**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "tokenType": "Bearer",
    "userId": 1,
    "name": "Jane Doe",
    "email": "jane.doe@example.com",
    "role": "PATIENT",
    "expiresIn": 86400000
  }
}
```

---

## User Endpoints — `/api/v1/users`

> All endpoints below require a valid `Authorization: Bearer <token>` header.

---

### GET `/api/v1/users`
Fetch all registered users.

**Response — 200 OK**
```json
{
  "success": true,
  "message": "Users fetched",
  "data": [
    {
      "userId": 1,
      "name": "Jane Doe",
      "role": "PATIENT",
      "email": "jane.doe@example.com",
      "phone": "9876543210",
      "status": "ACTIVE",
      "createdAt": "2024-06-01T10:00:00"
    },
    {
      "userId": 2,
      "name": "Dr. John Smith",
      "role": "DOCTOR",
      "email": "john.smith@example.com",
      "phone": "9123456789",
      "status": "ACTIVE",
      "createdAt": "2024-06-01T11:00:00"
    }
  ]
}
```

---

### GET `/api/v1/users/{id}`
Fetch a single user by ID.

**Response — 200 OK**
```json
{
  "success": true,
  "message": "User found",
  "data": {
    "userId": 1,
    "name": "Jane Doe",
    "role": "PATIENT",
    "email": "jane.doe@example.com",
    "phone": "9876543210",
    "status": "ACTIVE",
    "createdAt": "2024-06-01T10:00:00"
  }
}
```

**Response — 404 Not Found**
```json
{
  "success": false,
  "message": "User not found: 99"
}
```

---

### PUT `/api/v1/users/{id}`
Partially update a user's name, phone, or status. Only fields provided in the request body are updated.

**Request**
```json
{
  "name": "Jane D. Updated",
  "phone": "9000000001",
  "status": "INACTIVE"
}
```

> All fields are optional. Send only the fields you want to change.

**Response — 200 OK**
```json
{
  "success": true,
  "message": "User updated",
  "data": {
    "userId": 1,
    "name": "Jane D. Updated",
    "role": "PATIENT",
    "email": "jane.doe@example.com",
    "phone": "9000000001",
    "status": "INACTIVE",
    "createdAt": "2024-06-01T10:00:00"
  }
}
```

**Valid status values:** `ACTIVE` | `INACTIVE` | `SUSPENDED`

---

### DELETE `/api/v1/users/{id}`
Delete a user by ID.

**Response — 200 OK**
```json
{
  "success": true,
  "message": "User deleted",
  "data": null
}
```

**Response — 404 Not Found**
```json
{
  "success": false,
  "message": "User not found: 99"
}
```

---

### GET `/api/v1/users/{id}/audit-logs`
Fetch all audit log entries for a specific user.

**Response — 200 OK**
```json
{
  "success": true,
  "message": "Audit logs fetched",
  "data": [
    {
      "auditId": 1,
      "userId": 1,
      "action": "REGISTER",
      "resource": "User",
      "timestamp": "2024-06-01T10:00:00",
      "ipAddress": null,
      "outcome": "SUCCESS"
    },
    {
      "auditId": 2,
      "userId": 1,
      "action": "LOGIN",
      "resource": "Auth",
      "timestamp": "2024-06-01T10:05:00",
      "ipAddress": null,
      "outcome": "SUCCESS"
    }
  ]
}
```

---

## Error Responses

All errors follow the same structure:

```json
{
  "success": false,
  "message": "<error description>"
}
```

| HTTP Status | Scenario |
|---|---|
| `400 Bad Request` | Validation failed (missing/invalid fields) |
| `401 Unauthorized` | Invalid email or password |
| `404 Not Found` | User ID does not exist |
| `409 Conflict` | Email already registered |
| `500 Internal Server Error` | Unexpected server error |

**Example — Validation Error (400)**
```json
{
  "success": false,
  "message": "Name is required, Password min 8 chars"
}
```

**Example — Duplicate Email (409)**
```json
{
  "success": false,
  "message": "Email already registered: jane.doe@example.com"
}
```

---

## Health Check

```
GET http://localhost:8081/actuator/health
```

```json
{
  "status": "UP"
}
```

---

## Project Structure

```
src/main/java/com/healthcaregov/
├── config/
│   └── SecurityConfig.java          # JWT filter chain, CORS, session policy
├── exception/
│   ├── GlobalExceptionHandler.java  # Centralised error responses
│   ├── ResourceNotFoundException.java
│   ├── DuplicateResourceException.java
│   └── SlotUnavailableException.java
├── module/identity/
│   ├── controller/
│   │   ├── AuthController.java      # /api/v1/auth
│   │   └── UserController.java      # /api/v1/users
│   ├── dto/                         # Request / Response models
│   ├── entity/
│   │   ├── User.java                # users table
│   │   └── AuditLog.java            # audit_logs table
│   ├── repository/
│   │   ├── UserRepository.java
│   │   └── AuditLogRepository.java
│   └── service/
│       ├── AuthService.java         # Register + Login logic
│       └── UserService.java         # CRUD + Audit log retrieval
├── security/
│   ├── JwtUtil.java                 # Token generation & validation
│   ├── JwtAuthenticationFilter.java # Per-request JWT filter
│   └── UserDetailsServiceImpl.java  # Loads user from DB for Spring Security
└── HealthCareGovApplication.java
```
