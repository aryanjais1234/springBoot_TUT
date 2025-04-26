# Spring Boot REST API - Student Example

---

## Overview

This project demonstrates how to build a basic **Spring Boot REST API** to manage a list of students.  
It covers:

- Creating REST endpoints using **Spring Boot**
- Using **`@RestController`**, **`@RequestMapping`**, **`@GetMapping`**, **`@PathVariable`**
- Handling exceptions properly with **`@ControllerAdvice`** and **`@ExceptionHandler`**
- Returning customized **error responses** (`StudentErrorResponse`) for client errors

---

## Project Structure

| File | Purpose |
|:-----|:--------|
| `StudentRestController.java` | Main REST controller to expose student APIs |
| `StudentNotFoundException.java` | Custom exception for handling "student not found" |
| `StudentRestExceptionHandler.java` | Global exception handler for API errors |
| `StudentErrorResponse.java` | JavaBean for sending structured error messages |

---

## Features

1. **GET `/api/students`**
    - Returns a list of all students.

2. **GET `/api/students/{studentId}`**
    - Returns details of a student by their index.
    - Throws a custom exception if the student is not found.

3. **Global Exception Handling**
    - Handles specific `StudentNotFoundException` separately.
    - Handles all other exceptions generically.
    - Returns structured error responses with status code, message, and timestamp.

---

## Special Annotations Explained

| Annotation | Purpose |
|:-----------|:--------|
| `@RestController` | Marks the class as a REST controller, combines `@Controller` + `@ResponseBody`. |
| `@RequestMapping("/api")` | Sets a base path for all endpoints inside this controller. |
| `@PostConstruct` | Marks a method that runs **after dependency injection** is done — here, used to initialize sample data. |
| `@GetMapping("/students")` | Maps HTTP GET requests for `/api/students` to a method. |
| `@GetMapping("/students/{studentId}")` | Maps HTTP GET requests with a path variable (e.g., `/api/students/1`). |
| `@PathVariable` | Binds the `{studentId}` from the URL to the method parameter. |
| `@ControllerAdvice` | Declares a **global exception handler** for the application. |
| `@ExceptionHandler` | Handles specific exceptions thrown inside controller methods. |

---

## Sample Code Explained

---

### 1. `StudentRestController.java`
Handles REST API endpoints.

```java
@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> theStudents;

    @PostConstruct
    public void loadData() {
        theStudents = new ArrayList<>();
        theStudents.add(new Student("Poornima", "Patel"));
        theStudents.add(new Student("Mario", "Rossi"));
        theStudents.add(new Student("Mary", "Smith"));
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return theStudents;
    }

    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {
        if (studentId >= theStudents.size() || studentId < 0) {
            throw new StudentNotFoundException("Student id not found - " + studentId);
        }
        return theStudents.get(studentId);
    }
}
```

---

### 2. `StudentNotFoundException.java`
A custom exception that extends `RuntimeException`.

```java
public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String message) {
        super(message);
    }
}
```

When a student is not found, this exception is thrown.

---

### 3. `StudentRestExceptionHandler.java`
Handles all exceptions globally and returns structured error responses.

```java
@ControllerAdvice
public class StudentRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc) {
        StudentErrorResponse error = new StudentErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exc) {
        StudentErrorResponse error = new StudentErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
```

---

### 4. `StudentErrorResponse.java`
A simple Java Bean used to return error details to the client.

```java
public class StudentErrorResponse {
    private int status;
    private String message;
    private long timeStamp;

    // Constructors, Getters and Setters
}
```

Example JSON response when an error occurs:

```json
{
  "status": 404,
  "message": "Student id not found - 10",
  "timeStamp": 1714145678901
}
```

---

## Example API Usage

- **Get all students**  
  `GET http://localhost:8080/api/students`

- **Get a specific student**  
  `GET http://localhost:8080/api/students/0`

- **If you give invalid ID (e.g., `/students/100`)**, you will get:
  ```json
  {
    "status": 404,
    "message": "Student id not found - 100",
    "timeStamp": 1714145678901
  }
  ```

---

## Key Points

✅ Shows how to expose REST endpoints  
✅ Properly handles bad input with custom exceptions  
✅ Provides clean JSON error responses  
✅ Demonstrates core Spring Boot REST concepts in a simple, practical way

---

# 🚀 Conclusion

This mini-project covers the **foundation of building robust REST APIs** using Spring Boot.  
Once you are comfortable with this, you can extend it further:
- Add **POST**, **PUT**, and **DELETE** endpoints
- Integrate with a **Database (JPA/Hibernate)**
- Add **Validation (@Valid)** and **DTOs**

---
