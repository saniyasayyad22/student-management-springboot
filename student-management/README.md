# Student Management System
### REST API built with Spring Boot, JPA/Hibernate, MySQL

---

## Tech Stack
- **Java 17**
- **Spring Boot 3.2** — framework
- **Spring Data JPA + Hibernate** — ORM for database operations
- **MySQL** — relational database
- **Lombok** — reduces boilerplate code
- **Maven** — build tool

---

## Project Structure (MVC Architecture)
```
src/main/java/com/saniya/studentmanagement/
├── StudentManagementApplication.java   ← Entry point
├── controller/
│   └── StudentController.java          ← Handles HTTP requests
├── service/
│   └── StudentService.java             ← Interface (contract)
├── serviceimpl/
│   └── StudentServiceImpl.java         ← Business logic
├── repository/
│   └── StudentRepository.java          ← Database operations
├── model/
│   └── Student.java                    ← JPA Entity (maps to DB table)
├── dto/
│   └── StudentDTO.java                 ← Data Transfer Object
└── exception/
    ├── ResourceNotFoundException.java  ← Custom 404 exception
    └── GlobalExceptionHandler.java     ← Centralized error handling
```

---

## Setup Instructions

### 1. Prerequisites
- Java 17+
- MySQL installed and running
- Maven installed
- IDE: IntelliJ IDEA (recommended) or Eclipse

### 2. Database Setup
Open MySQL and run:
```sql
CREATE DATABASE student_db;
```
(The app will create tables automatically via JPA `ddl-auto=update`)

### 3. Configure Database
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 4. Run the Application
```bash
mvn spring-boot:run
```
App starts on: `http://localhost:8080`

---

## API Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| POST   | /api/students | Create new student |
| GET    | /api/students | Get all students |
| GET    | /api/students/{id} | Get student by ID |
| PUT    | /api/students/{id} | Update student |
| DELETE | /api/students/{id} | Delete student |
| GET    | /api/students/department/{dept} | Filter by department |
| GET    | /api/students/search?name=xyz | Search by name |
| GET    | /api/students/cgpa?min=8.0 | Filter by CGPA |

---

## Sample API Requests (test with Postman)

### Create Student
```
POST http://localhost:8080/api/students
Content-Type: application/json

{
  "firstName": "Saniya",
  "lastName": "Sayyad",
  "email": "saniya@example.com",
  "phoneNumber": "9322377846",
  "department": "Information Technology",
  "cgpa": 8.84
}
```

### Get All Students
```
GET http://localhost:8080/api/students
```

### Update Student
```
PUT http://localhost:8080/api/students/1
Content-Type: application/json

{
  "firstName": "Saniya",
  "lastName": "Sayyad",
  "email": "saniya.updated@example.com",
  "phoneNumber": "9322377846",
  "department": "Computer Engineering",
  "cgpa": 9.0
}
```

### Search by Name
```
GET http://localhost:8080/api/students/search?name=saniya
```

---

## Key Concepts Used (for interviews)

### 1. Layered Architecture (MVC)
- **Controller** → receives HTTP request
- **Service** → handles business logic
- **Repository** → talks to database
- Each layer has a single responsibility

### 2. JPA / Hibernate
- `@Entity` maps Java class to DB table
- `@Id` + `@GeneratedValue` = auto-increment primary key
- JpaRepository provides CRUD methods without writing SQL

### 3. Dependency Injection
- `@Autowired` lets Spring inject dependencies automatically
- No manual `new` object creation needed

### 4. DTOs (Data Transfer Objects)
- We never expose database entities directly
- DTOs separate the API layer from the data layer

### 5. Exception Handling
- Custom `ResourceNotFoundException` for 404
- `GlobalExceptionHandler` handles all errors in one place

### 6. REST API Design
- Correct HTTP methods: GET, POST, PUT, DELETE
- Correct status codes: 200, 201, 404, 400, 500
- Input validation with `@Valid`, `@NotBlank`, `@Email`
