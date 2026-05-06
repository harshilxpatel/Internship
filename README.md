🚀 Learning Management System (LMS) – Backend Engineering Internship Progress


📌 Project Overview

This project was developed as part of a Backend Engineering Internship Assignment focused on building production-ready backend systems using:

Java 21
Spring Boot 3
Clean Architecture
Testing & Performance Optimization

The goal is to simulate a real-world scalable Learning Management System (LMS) with:

Student Management
Course Management
Enrollment Engine
Search & Pagination
Production-grade Exception Handling
🛠️ Tech Stack
Java 21
Spring Boot 3
Spring Data JPA
Hibernate ORM
PostgreSQL (Docker)
H2 (Testing)
Maven
Lombok
Swagger / OpenAPI
JUnit 5
Mockito
MockMvc
JaCoCo
JPA Specification

🏗️ Project Architecture
Controller Layer
Service Layer
Repository Layer
DTO Layer
Entity Layer
Mapper Layer
Specification Layer
Exception Layer
Test Layer


✅ Week 1 – Foundation

Day 1
Project cloned and setup
Docker PostgreSQL configured
Application successfully started
Project structure understood

Day 2
Studied Student Entity
Understood:
@Entity
@Id
@GeneratedValue
Created StudentRepository

Day 3
Implemented StudentService
create()
findById()
Added duplicate email validation
Wrote unit tests using Mockito

Day 4
Implemented StudentController
POST /api/v1/students
GET /api/v1/students/{id}
Added validation using @Valid

Day 5
Implemented:
Update Student API
Soft Delete
Added Integration Test using MockMvc


✅ Week 2 – Courses + Validation + Exception Handling

Day 6
Implemented full Course Module
CRUD APIs
Added:
      Duplicate course code validation
      Soft delete

Day 7
Wrote Unit Tests for CourseService
Covered:
        Success cases
        Duplicate cases

Day 8 – Global Exception Handling
Implemented:
            @RestControllerAdvice
Created:
        ResourceNotFoundException
        DuplicateResourceException
        Standard error response structure

Day 9
Tested all error scenarios:
400 Bad Request
404 Not Found
409 Conflict
Wrote integration tests

Day 10 – Swagger Documentation
Integrated Swagger using SpringDoc
Added annotations:
                  @Tag
                  @Operation
                  @ApiResponse


✅ Week 3 – Advanced Backend Engineering

Day 11 – Enrollment Design
Designed Enrollment entity:
                          ManyToOne relationships
                          Status enum (ENROLLED / UNENROLLED)

Day 12 – Enrollment Service
Implemented:
            enroll(studentId, courseId)
            unenroll(studentId, courseId)
Added:
      Duplicate prevention
      Business validation

Day 13 – Query APIs
GET /api/v1/students/{id}/courses
GET /api/v1/courses/{id}/students

Day 14 – N+1 Problem Optimization
Understood N+1 problem
Fixed using:
            JOIN FETCH
            Optimized DB performance

Day 15 – Search + Pagination
Implemented dynamic search using JPA Specification
GET /api/v1/courses/search
Supports:
         title (LIKE)
         code (LIKE)
         pagination
         sorting

🧪 Testing Coverage
Unit Testing (Mockito)
Integration Testing (MockMvc)
Error Scenario Testing
Service Layer Testing

✅ Week 4 – Production Readiness

Day 16 – JaCoCo
Ran:
    mvn clean test
    Achieved good coverage on service layer

Day 17 – Full API Testing
All APIs tested:
          Student APIs ✅
          Course APIs ✅
          Enrollment APIs ✅

Covered:
        200 OK
        400 Bad Request
        404 Not Found
        409 Conflict

🔥 Day 18 – API Standardization + Refactoring
✅ API Response Standardization
{
"timestamp": "2026-05-04T10:30:00",
"status": 200,
"message": "Success",
"data": {}
}

Improvements:
             Unified responses across APIs
             Better frontend integration
             Clean API contract
✅ Project Structure Refactoring
service
├── interfaces

service.impl
├── implementations

✔ Cleaner architecture
✔ Better maintainability

🚀 Day 19 – Documentation (IMPORTANT – PDF EXACT REQUIREMENT)

According to PDF:

“Write README: project overview, how to run, ER diagram, architectural decisions”

✅ How to Run Project Locally
1. Clone Repository
   git clone <repo-url>
   cd lms
2. Start Database (Docker)
   docker-compose up -d
3. Run Application
   mvn spring-boot:run
4. Swagger UI
   http://localhost:8081/swagger-ui.html
   🗄️ Database Design (ER Concept)

Entities:
         Student
         Course
         Enrollment

Relationships:

One Student → Many Enrollments
One Course → Many Enrollments

🧠 Architectural Decisions
1. DTO Pattern
   Used to separate API layer from DB layer
   Prevents exposing internal entities
2. Manual Mapper (instead of MapStruct)
   Simple and controlled mapping
   Easier for learning phase
3. JPA Specification
   Used for dynamic filtering
   Clean and scalable query building
4. Soft Delete
   Prevents data loss
   Maintains audit history
5. Global Exception Handling
   Centralized error handling
   Consistent API response
   📬 API Testing
   All APIs tested using:
   Postman
   Swagger UI
   🔥 Final Outcome (Day 19)

✔ Production-ready backend
✔ Clean architecture
✔ Tested APIs
✔ Standardized responses
✔ Proper documentation

## 🚀 Day 20 – Production Readiness & Deployment

Improvements Done
- Prepared project for production deployment
- Verified Docker PostgreSQL setup
- Cleaned Git commit history using interactive rebase
- Improved README documentation for better understanding

🌐 How to Deploy (Basic)
1. Build project:
   mvn clean package

2. Run application:
   java -jar target/lms.jar

3. Run database using Docker:
   docker-compose up -d

📌 Future Improvements
- Add JWT Authentication
- Add Role-Based Authorization
- Deploy on Cloud (AWS / Render)
- CI/CD using GitHub Actions

👨‍💻 Developed By

Harshil Patel
Java Backend Developer 🚀

