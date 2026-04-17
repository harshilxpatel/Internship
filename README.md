# Internship

Day 1
- Project setup
- Docker + PostgreSQL configured
- Application running successfully

Day 2
- Student Entity created
- JPA annotations implemented
- Repository layer created

Day 3
- Service layer implemented
- Business logic added (duplicate email check)
- Unit tests written using JUnit & Mockito

Day 4
- REST APIs created (POST, GET)
- Validation added using @Valid
    
Day 5
- PUT API implemented (Update Student)
- Soft delete implemented
- Only non-deleted students fetched

API Endpoints (Student)
| Method | Endpoint 
|--------|---------
| POST   | /api/v1/students 
| GET    | /api/v1/students 
| GET    | /api/v1/students/{id} 
| PUT    | /api/v1/students/{id} 
| DELETE | /api/v1/students/{id} 

Day 6

Implemented full Course module following layered architecture (Controller → Service → Repository) similar to Student module.

* Course Entity created
* CourseRepository implemented
* CourseService & CourseServiceImpl created
* Business logic added (duplicate course code check)
* Full Course CRUD APIs implemented (POST, GET, PUT, DELETE)
* Soft delete implemented for Course
* Only non-deleted courses fetched

API Endpoints (Course)

| Method | Endpoint             |
| ------ | -------------------- |
| POST   | /api/v1/courses      |
| GET    | /api/v1/courses      |
| GET    | /api/v1/courses/{id} |
| PUT    | /api/v1/courses/{id} |
| DELETE | /api/v1/courses/{id} |
