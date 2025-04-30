# 📘 Spring Boot REST API – Course Breakdown and Key Concepts

## ✅ Introduction & Setup
- **Course Objective**: Build full-featured Spring Boot REST APIs from scratch using best practices.
- **Tools Used**: Java 17+, Spring Boot, Maven, Postman, MySQL/H2, IntelliJ/Eclipse.
- **Development Environment**: Ensure Java and IDE setup with Maven and Spring Boot support.

---

## 🧰 Maven and Spring Boot Fundamentals
- **Maven Overview & Project Structure**: Understand POM.xml, dependencies, and build lifecycle.
- **Spring Boot Initializr**: Tool to bootstrap Spring projects easily.
- **Project Files Overview**: Explanation of auto-generated files like `Application.java`, `application.properties`, etc.
- **Starters & DevTools**: Learn `spring-boot-starter-web`, `spring-boot-devtools`, etc.

---

## 🔁 Configuration & Actuator
- **Custom Properties**: Define and inject values using `@Value` or `@ConfigurationProperties`.
- **Actuator**: Monitor and manage applications using endpoints like `/actuator/health`.

---

## 🔧 Running Apps
- **Command Line Execution**: Use `mvn spring-boot:run` or `java -jar` to run apps.
- **Application Properties**:
    - `server.port`: Change default port.
    - `spring.application.name`: Set the app name.
    - `spring.data.rest.base-path`: Customize REST base path.

---

## 💡 Dependency Injection (DI) and Inversion of Control (IoC)
- **Inversion of Control**: Framework manages object creation.
- **Dependency Injection**: Inject required objects into a class.

### Types of Injection:
- **Constructor Injection**: Preferred for immutability.
- **Setter Injection**: Optional dependencies.
- **Field Injection**: Least recommended (harder to test).

---

## 🔍 Component Scanning & Bean Management
- **Component Scanning**: Auto-detect components using:
    - `@Component`: Generic stereotype for any Spring-managed component.
    - `@Service`: For service classes.
    - `@Repository`: For DAO layer (adds exception translation).
    - `@Controller` / `@RestController`: Web layer (explained below).
- **@Autowired**: Automatically inject dependencies.
- **@Qualifier**: Use when multiple beans of the same type exist.
- **@Primary**: Set default bean when multiple candidates exist.
- **@Lazy**: Delay bean initialization until it's first used.
- **@Scope**: Control bean scope like `singleton` or `prototype`.

---

## 🌱 Bean Lifecycle
- **Lifecycle Annotations**:
    - `@PostConstruct`: Run code after bean creation.
    - `@PreDestroy`: Run code before bean destruction.
- **Prototype Scope Caveat**: Spring doesn't manage destruction of prototype beans.

---

## 🧾 Java-Based Configuration
- **@Configuration**: Declare configuration class.
- **@Bean**: Define beans explicitly inside config class.

---

## 🌐 REST Services and JSON
- **What is REST?**: Architectural style using HTTP methods.
- **REST Controller**: Handle REST endpoints using:
    - `@RestController`: Combines `@Controller` and `@ResponseBody`.
    - `@RequestMapping`: Base path.
    - `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`: Map HTTP verbs to methods.
    - `@PathVariable`: Bind URL segment to method parameter.
    - `@RequestBody`: Bind JSON to Java object.

---

## ⚠️ Exception Handling
- **@ExceptionHandler**: Handle specific exceptions.
- **@ControllerAdvice**: Handle exceptions globally.
- **Error Response**: Send structured JSON errors with fields like `status`, `message`, `timestamp`.

Example:
```json
{
  "status": 404,
  "message": "Employee not found - id: 10",
  "timestamp": 1714145678901
}
```

