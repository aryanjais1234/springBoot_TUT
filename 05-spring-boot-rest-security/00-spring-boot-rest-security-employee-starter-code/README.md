# Spring Boot REST Security Employee Management

This project is a Spring Boot RESTful web service for managing employees with integrated security using Spring Security. It demonstrates how to secure REST APIs with role-based access control and JDBC authentication.

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Database Setup](#database-setup)
- [Security Configuration](#security-configuration)
- [API Endpoints](#api-endpoints)
- [Testing the Application](#testing-the-application)

## Project Overview

This project provides a REST API for managing employee data. It includes security features that restrict access to API endpoints based on user roles. Authentication is done via HTTP Basic Authentication, and user details are stored in a relational database accessed via JDBC with custom table names.

## Features

- RESTful API for employee management (CRUD operations)
- Role-based access control:
    - `EMPLOYEE` role: Can view employee data (GET requests)
    - `MANAGER` role: Can add and update employee data (POST and PUT requests)
    - `ADMIN` role: Can delete employee data (DELETE requests)
- JDBC-based user authentication and authorization with custom table names
- HTTP Basic Authentication
- CSRF protection disabled for stateless REST API
- Support for partial updates using PATCH method

## Technologies Used

- Java 17+
- Spring Boot 3.x
- Spring Security 6.x
- Spring Data JPA
- MySQL database
- Maven build tool

## Project Structure

```
src/main/java/com/luv2code/springboot/cruddemo/
├── CruddemoApplication.java                  # Main application class
├── dao/
│   └── EmployeeRepository.java               # JPA repository for data access
├── entity/
│   └── Employee.java                         # JPA entity representing an employee
├── rest/
│   └── EmployeeRestController.java           # REST controller exposing API endpoints
├── security/
│   └── DemoSecurityConfig.java               # Security configuration
└── service/
    ├── EmployeeService.java                  # Service interface
    └── EmployeeServiceImpl.java              # Service implementation
```

## Getting Started

### Prerequisites

- Java JDK 17 or higher installed
- Maven installed
- MySQL database
- Postman or any REST client for testing APIs

### Running the Application

1. Clone the repository:

   ```bash
   git clone <repository-url>
   cd 00-spring-boot-rest-security-employee-starter-code
   ```

2. Configure your database connection in `application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/employee_directory
   spring.datasource.username=springstudent
   spring.datasource.password=springstudent
   server.port=8085
   ```

3. Create the required database tables by running the SQL scripts in the `sql-scripts` directory:
   - `employee-directory.sql`: Creates and populates the employee table
   - `06-setup-spring-security-demo-database-bcrypt-custom-table-names.sql`: Sets up security tables

4. Build and run the application:

   ```bash
   mvn spring-boot:run
   ```

5. The application will start on `http://localhost:8085`.

## Database Setup

The application uses two main database components:

1. **Employee Table**: Stores employee information
   - Fields: id, first_name, last_name, email

2. **Security Tables**: Custom tables for authentication and authorization
   - `members`: Stores user credentials (user_id, pw, active)
   - `roles`: Stores user roles (user_id, role)

### Table: `members`

| Column  | Type    | Description                  |
|---------|---------|------------------------------|
| user_id | VARCHAR | Username (primary key)       |
| pw      | VARCHAR | Password (BCrypt hashed)     |
| active  | BOOLEAN | Whether the user is active   |

### Table: `roles`

| Column  | Type    | Description                  |
|---------|---------|------------------------------|
| user_id | VARCHAR | Username (foreign key)       |
| role    | VARCHAR | Role assigned to the user    |

### Predefined Users

| Username | Password | Roles                               |
|----------|----------|-------------------------------------|
| john     | fun123   | EMPLOYEE                            |
| mary     | fun123   | EMPLOYEE, MANAGER                   |
| susan    | fun123   | EMPLOYEE, MANAGER, ADMIN            |

## Security Configuration

The security configuration in `DemoSecurityConfig.java` uses JDBC authentication with custom queries to retrieve user details and authorities from the database:

```java
// Define query to retrieve a user by username
jdbcUserDetailsManager.setUsersByUsernameQuery(
        "select user_id, pw, active from members where user_id=?"
);

// Define query to retrieve the authorities/roles by username
jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
        "select user_id, role from roles where user_id=?"
);
```

HTTP security is configured with role-based access control:

```java
hhttp.authorizeHttpRequests(configurer ->
        configurer
                .requestMatchers(HttpMethod.GET,"/api/employees").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET,"/api/employees/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.POST,"/api/employees").hasRole("MANAGER")
                .requestMatchers(HttpMethod.PUT,"/api/employees").hasRole("MANAGER")
                .requestMatchers(HttpMethod.DELETE,"/api/employees/**").hasRole("ADMIN")
);
```

Key security features:
- HTTP Basic Authentication
- Role-based access control
- CSRF protection disabled for REST API
- Custom database schema for user authentication and authorization

## API Endpoints

| Method | Endpoint                   | Description               | Role Required |
|--------|----------------------------|---------------------------|---------------|
| GET    | `/api/employees`           | Get all employees         | EMPLOYEE      |
| GET    | `/api/employees/{id}`      | Get employee by ID        | EMPLOYEE      |
| POST   | `/api/employees`           | Add a new employee        | MANAGER       |
| PUT    | `/api/employees`           | Update an existing employee| MANAGER      |
| PATCH  | `/api/employees/{id}`      | Partially update an employee| MANAGER     |
| DELETE | `/api/employees/{id}`      | Delete an employee by ID  | ADMIN         |

## Testing the Application

### Using cURL

#### Get All Employees
```bash
curl -X GET http://localhost:8085/api/employees -u john:fun123
```

#### Get Employee by ID
```bash
curl -X GET http://localhost:8085/api/employees/1 -u john:fun123
```

#### Create New Employee (MANAGER or ADMIN role required)
```bash
curl -X POST http://localhost:8085/api/employees -u mary:fun123 \
  -H "Content-Type: application/json" \
  -d '{"firstName": "New", "lastName": "Employee", "email": "new@luv2code.com"}'
```

#### Update Employee (MANAGER or ADMIN role required)
```bash
curl -X PUT http://localhost:8085/api/employees -u mary:fun123 \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "firstName": "Updated", "lastName": "Employee", "email": "updated@luv2code.com"}'
```

#### Partially Update Employee (MANAGER or ADMIN role required)
```bash
curl -X PATCH http://localhost:8085/api/employees/1 -u mary:fun123 \
  -H "Content-Type: application/json" \
  -d '{"firstName": "Partially Updated"}'
```

#### Delete Employee (ADMIN role required)
```bash
curl -X DELETE http://localhost:8085/api/employees/1 -u susan:fun123
```

### Using Postman

1. Set the request URL to the appropriate endpoint
2. Select the HTTP method (GET, POST, PUT, PATCH, DELETE)
3. For POST, PUT, and PATCH requests, add a JSON body with the employee data
4. Set up Basic Authentication with the appropriate username and password
5. Send the request and check the response