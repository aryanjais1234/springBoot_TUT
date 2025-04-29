📘 Spring Boot REST API – Beginner to Advanced Guide (with CRUD Example)
🎯 Objective
This guide walks you through building a Spring Boot REST API for managing entities (like Students or Employees) in a clean, layered, and scalable way. It starts with a simple controller and ends with advanced topics like Spring Data JPA, global exception handling, and automatic REST exposure with Spring Data REST.

🛠️ Tech Stack
Java 17+

Spring Boot (Web, JPA, Spring Data REST)

Hibernate

MySQL / H2

Postman (for testing)

Maven

🧱 Architecture Diagram
Here’s a high-level architecture of how your API is structured:

csharp
Copy
Edit
[Client / Postman]
|
v
[RestController] <-- maps HTTP to Java methods
|
v
[Service Layer] <-- business logic
|
v
[DAO / Repository] <-- data access
|
v
[Database]
🧪 Phase 1: Basic REST API with Controller Only
✅ Features:
Simple REST controller to handle GET requests

Handles list of Student objects

Exception handling using @ControllerAdvice

🔄 Endpoints
GET /api/students

GET /api/students/{id}

📄 Key Files
StudentRestController.java

StudentNotFoundException.java

StudentRestExceptionHandler.java

StudentErrorResponse.java

🛠 Phase 2: Full CRUD with DAO, Service, JPA
✅ Features:
Adds Employee Entity

DAO layer using EntityManager

Service layer with @Transactional

Full CRUD: GET, POST, PUT, DELETE

📂 Folder Structure
css
Copy
Edit
com.springboot.crudDemo
├── entity
│ └── Employee.java
├── dao
│ └── EmployeeDAO.java
│ └── EmployeeDAOJpaImpl.java
├── service
│ └── EmployeeService.java
│ └── EmployeeServiceImpl.java
├── rest
│ └── EmployeeRestController.java
🔄 Endpoints
GET /api/employees

GET /api/employees/{id}

POST /api/employees

PUT /api/employees

DELETE /api/employees/{id}

🚀 Phase 3: Simplified REST API using Spring Data JPA
✅ Features:
Removes manual DAO code

Uses JpaRepository interface

Less boilerplate, same functionality

📄 Key Files
EmployeeRepository.java (extends JpaRepository)

No EntityManager code needed

✨ Phase 4: Zero-code REST API using Spring Data REST
✅ Features:
No controller or service layer required

Just define Entity + Repository

REST endpoints auto-generated

⚙️ Configuration:
properties
Copy
Edit
spring.data.rest.base-path=/magic-api
🔄 Auto Endpoints
GET /magic-api/employees

POST /magic-api/employees

PUT /magic-api/employees/{id}

DELETE /magic-api/employees/{id}

⚠️ Exception Handling
Global Exception Handling
@ControllerAdvice for global errors

@ExceptionHandler for custom exceptions

Example JSON Error Response
json
Copy
Edit
{
"status": 404,
"message": "Student id not found - 100",
"timeStamp": 1714145678901
}
🧼 Summary

Stage Features Covered
1 Basic REST, Controller, Custom Exception
2 Full CRUD with DAO + Service + JPA
3 Switched to Spring Data JPA
4 Zero-code API with Spring Data REST
