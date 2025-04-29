# Spring Boot CRUD REST API Demo

This project is a basic Spring Boot REST API that performs CRUD operations (Create, Read, Update, Delete) on an `Employee` entity using JPA and DAO layers.

---

## 📁 Project Structure

```
com.springboot.crudDemo
│
├── entity          // Contains JPA entity classes
├── dao             // Data Access Layer using JPA
├── service         // Service Layer (Business Logic)
└── rest            // REST Controllers
```

---

## 🧱 Entity Layer

### `Employee.java`

This is a simple JPA entity mapped to the `employee` table in the database.

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

## 📊 DAO Layer

### `EmployeeDAO`

An interface defining data access methods.

```java
public interface EmployeeDAO {
    List<Employee> findAll();
    Employee findById(int theId);
    Employee save(Employee theEmployee);
    void deleteById(int theId);
}
```

### `EmployeeDAOJpaImpl`

Implements the `EmployeeDAO` using JPA `EntityManager`.

```java
@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {
    @Autowired
    private EntityManager entityManager;

    public List<Employee> findAll() {
        return entityManager.createQuery("from Employee", Employee.class).getResultList();
    }

    public Employee findById(int theId) {
        return entityManager.find(Employee.class, theId);
    }

    public Employee save(Employee theEmployee) {
        return entityManager.merge(theEmployee);
    }

    public void deleteById(int theId) {
        Employee emp = entityManager.find(Employee.class, theId);
        entityManager.remove(emp);
    }
}
```

---

## ⚙️ Service Layer

### `EmployeeService`

Defines methods for business logic.

```java
public interface EmployeeService {
    List<Employee> findsAll();
    Employee findById(int theId);
    Employee save(Employee theEmployee);
    void deleteById(int theId);
}
```

### `EmployeeServiceImpl`

Implements the service logic and uses the DAO layer.

```java
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDAO employeeDAO;

    @Transactional
    public Employee save(Employee theEmployee) {
        return employeeDAO.save(theEmployee);
    }
}
```

---

## 🌐 REST Controller

### `EmployeeRestController`

Exposes REST endpoints for CRUD operations.

```java
@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    @Autowired
    private EmployeeService employeeService;

    // GET all
    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findsAll();
    }

    // GET by ID
    @GetMapping("/employees/{employeeId}")
    public Employee findById(@PathVariable int employeeId) {
        Employee emp = employeeService.findById(employeeId);
        if (emp == null) throw new RuntimeException("Employee not found");
        return emp;
    }

    // POST create
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee) {
        theEmployee.setId(0);  // force insert
        return employeeService.save(theEmployee);
    }

    // PUT update
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee) {
        return employeeService.save(theEmployee);
    }

    // DELETE
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        Employee emp = employeeService.findById(employeeId);
        if (emp == null) throw new RuntimeException("Employee not found");
        employeeService.deleteById(employeeId);
        return "Deleted employee id - " + employeeId;
    }
}
```

---

## 🛠 Features Covered

| Feature                         | Description                                |
|----------------------------------|--------------------------------------------|
| Create Spring Boot REST Project | Setup with Spring Web + JPA + H2/MySQL     |
| DAO Layer                       | Custom DAO using EntityManager             |
| Service Layer                   | Contains business logic and uses DAO       |
| CRUD Operations                 | Full support for GET, POST, PUT, DELETE    |
| JSON Handling                   | Uses @RequestBody and @PathVariable        |
| Exception Handling (Basic)      | Throws runtime error if employee not found |

---

## 🚀 Running the Project

1. Clone the repository
2. Configure DB (e.g., H2/MySQL) in `application.properties`
3. Run `CrudDemoApplication.java`
4. Use Postman or Swagger to hit the endpoints (e.g., `/api/employees`)

---
