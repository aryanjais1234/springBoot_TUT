# Spring Boot REST Security Employee Management

This project is a Spring Boot RESTful web service for managing employees with integrated security using Spring Security. It demonstrates how to secure REST APIs with role-based access control and JDBC authentication.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Database Setup](#database-setup)
- [Security Configuration](#security-configuration)
- [API Endpoints](#api-endpoints)
- [Testing the Application](#testing-the-application)
- [Further Enhancements](#further-enhancements)
- [License](#license)

---

## Project Overview

This project provides a REST API for managing employee data. It includes security features that restrict access to API endpoints based on user roles. Authentication is done via HTTP Basic Authentication, and user details are stored in a relational database accessed via JDBC.

---

## Features

- RESTful API for employee management (CRUD operations)
- Role-based access control:
    - `EMPLOYEE` role: Can view employee data (GET requests)
    - `MANAGER` role: Can add and update employee data (POST and PUT requests)
    - `ADMIN` role: Can delete employee data (DELETE requests)
- JDBC-based user authentication and authorization
- HTTP Basic Authentication
- CSRF protection disabled for stateless REST API
- Easily extensible security configuration

---

## Technologies Used

- Java 17+
- Spring Boot 3.x
- Spring Security 6.x
- Spring Data JPA (optional, depending on your employee data access implementation)
- JDBC for user authentication
- H2 / MySQL / PostgreSQL (or any relational database)
- Maven or Gradle build tool

---

## Getting Started

### Prerequisites

- Java JDK 17 or higher installed
- Maven or Gradle installed
- A relational database (H2, MySQL, PostgreSQL, etc.)
- Postman or any REST client for testing APIs

### Running the Application

1. Clone the repository:

   ```bash
   git clone <repository-url>
   cd <project-directory>
   ```

2. Configure your database connection in `application.properties` or `application.yml`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

   spring.jpa.hibernate.ddl-auto=update
   ```

3. Create the required tables (`members` and `roles`) and insert user data (see [Database Setup](#database-setup)).

4. Build and run the application:

   ```bash
   mvn spring-boot:run
   ```

   or if using Gradle:

   ```bash
   ./gradlew bootRun
   ```

5. The application will start on `http://localhost:8080`.

---

## Database Setup

The security configuration expects two tables: `members` and `roles`.

### Table: `members`

| Column  | Type    | Description                  |
|---------|---------|------------------------------|
| user_id | VARCHAR | Username (primary key)       |
| pw      | VARCHAR | Password (hashed or plain)   |
| active  | BOOLEAN | Whether the user is active   |

### Table: `roles`

| Column  | Type    | Description                  |
|---------|---------|------------------------------|
| user_id | VARCHAR | Username (foreign key)       |
| role    | VARCHAR | Role assigned to the user    |

### Example SQL for MySQL

```sql
CREATE TABLE members (
    user_id VARCHAR(50) NOT NULL PRIMARY KEY,
    pw VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL
);

CREATE TABLE roles (
    user_id VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES members(user_id)
);

-- Insert sample users
INSERT INTO members (user_id, pw, active) VALUES
('john', '{noop}test123', true),
('mary', '{noop}test123', true),
('susan', '{noop}test123', true);

-- Insert roles
INSERT INTO roles (user_id, role) VALUES
('john', 'ROLE_EMPLOYEE'),
('mary', 'ROLE_EMPLOYEE'),
('mary', 'ROLE_MANAGER'),
('susan', 'ROLE_EMPLOYEE'),
('susan', 'ROLE_MANAGER'),
('susan', 'ROLE_ADMIN');
```

> **Note:** Passwords are stored with `{noop}` prefix for plain text in this example. For production, use password encoding.

---

## Security Configuration

- **UserDetailsManager** is configured to use JDBC with custom queries to fetch users and roles.
- HTTP Basic Authentication is enabled.
- CSRF protection is disabled to allow POST, PUT, DELETE requests from REST clients.
- Role-based access control is configured as follows:

| HTTP Method | Endpoint           | Required Role |
|-------------|--------------------|---------------|
| GET         | `/api/employees`   | EMPLOYEE      |
| GET         | `/api/employees/**`| EMPLOYEE      |
| POST        | `/api/employees`   | MANAGER       |
| PUT         | `/api/employees`   | MANAGER       |
| DELETE      | `/api/employees/**`| ADMIN         |

---

## API Endpoints

| Method | Endpoint             | Description               | Role Required |
|--------|----------------------|---------------------------|---------------|
| GET    | `/api/employees`     | Get all employees         | EMPLOYEE      |
| GET    | `/api/employees/{id}`| Get employee by ID        | EMPLOYEE      |
| POST   | `/api/employees`     | Add a new employee        | MANAGER       |
| PUT    | `/api/employees`     | Update an existing employee| MANAGER      |
| DELETE | `/api/employees/{id}`| Delete an employee by ID  | ADMIN         |

---




