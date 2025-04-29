# Spring Boot CRUD REST API with Spring Data JPA

This project is a RESTful web service built using **Spring Boot**, **Spring Data JPA**, and **Spring Web** to manage `Employee` data with full CRUD operations — now using **`JpaRepository`** instead of writing custom DAO code manually.

---

## ✅ What Changed in This Version?

| Previous Setup               | New Setup                                 |
|-----------------------------|--------------------------------------------|
| Custom DAO Interface         | Replaced with `JpaRepository`             |
| DAO Implementation (with EM) | Removed                                    |
| EntityManager                | No longer needed                          |
| Less code, same functionality| Thanks to Spring Data JPA                 |

---

## 📁 Project Structure (Updated)

```
com.springboot.crudDemo
│
├── entity          // Employee JPA entity
├── dao             // Contains EmployeeRepository (extends JpaRepository)
├── service         // Business logic using repository
└── rest            // REST API endpoints
```

---

## 🧱 Employee Entity

```java
@Entity
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String email;
}
```

---

## 🧠 What is `JpaRepository`?

Spring Data JPA provides the `JpaRepository` interface, which offers ready-to-use methods for CRUD operations, eliminating the need to write boilerplate code.

### ✨ How it Works

- Spring Boot automatically **generates the implementation** at runtime.
- It uses **Hibernate** (or your chosen JPA provider) under the hood.
- You only **declare an interface** that extends `JpaRepository`, and Spring injects the necessary logic for operations like `findAll()`, `findById()`, `save()`, `deleteById()`, etc.

---

## 🧾 Repository Layer

### `EmployeeRepository.java`

```java
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // No need to write implementation code.
}
```

---

## ⚙️ Service Layer (Refactored)

### `EmployeeServiceImpl.java`

```java
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findsAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int theId) {
        return employeeRepository.findById(theId)
               .orElseThrow(() -> new RuntimeException("Employee not found - " + theId));
    }

    @Override
    public Employee save(Employee theEmployee) {
        return employeeRepository.save(theEmployee);
    }

    @Override
    public void deleteById(int theId) {
        employeeRepository.deleteById(theId);
    }
}
```

---

## 📡 REST Controller

Same as before, your REST controller uses the service to provide the API:

```java
@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findsAll();
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        employee.setId(0); // Force insert
        return employeeService.save(employee);
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @GetMapping("/employees/{employeeId}")
    public Employee findById(@PathVariable int employeeId) {
        return employeeService.findById(employeeId);
    }

    @DeleteMapping("/employees/{employeeId}")
    public String delete(@PathVariable int employeeId) {
        employeeService.deleteById(employeeId);
        return "Deleted employee id - " + employeeId;
    }
}
```

---

## 🔧 Key Annotations Explained

| Annotation              | Purpose |
|-------------------------|---------|
| `@Repository`           | Marks the interface for Spring Data JPA recognition. (No need in this version unless adding custom methods.) |
| `@Service`              | Denotes the business service class. |
| `@Transactional`        | Manages database transaction scope. |
| `@RestController`       | Combines `@Controller` and `@ResponseBody`. |
| `@RequestMapping("/api")` | Prefixes all API endpoints. |
| `@GetMapping`, `@PostMapping` etc. | Maps HTTP methods to controller methods. |
| `@Autowired`            | Injects dependencies automatically. |

---

## 🚀 How to Run

1. Clone the repository
2. Configure your `application.properties` for H2/MySQL
3. Run the `CrudDemoApplication.java` main class
4. Use Postman or browser to test:

- `GET /api/employees`
- `GET /api/employees/{id}`
- `POST /api/employees`
- `PUT /api/employees`
- `DELETE /api/employees/{id}`

---

## 🧼 Summary

- Switched to **Spring Data JPA** for cleaner and faster development.
- Removed boilerplate code.
- Reduced bugs and improved maintainability.

---

