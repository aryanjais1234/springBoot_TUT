
# 📘 Spring Boot Beginner Course – Full Guide with Practical Examples

## 🎯 Overview

This course provides a comprehensive introduction to **Spring Boot**, from setting up your development environment to building and running RESTful web applications. You'll learn key concepts like **Maven**, **REST controllers**, **application configuration**, and **monitoring with Actuator**, explained with demos and coding exercises.



## ✅ Sample Example: Create a REST Controller

### 📁 File: `HelloController.java`

```java
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot!";
    }
}
```

### 🔍 Explanation:

- `@RestController`: Tells Spring Boot that this class handles **HTTP requests**. It combines `@Controller` and `@ResponseBody`.
- `@GetMapping("/hello")`: Maps **GET requests** (like when you type a URL in a browser) to this method. If you go to `localhost:8080/hello`, it shows "Hello, Spring Boot!".

---

## 🔧 Important Spring Boot Annotations (Explained in Layman's Terms)

| Annotation          | What It Does (Simply) |
|---------------------|-----------------------|
| `@SpringBootApplication` | This is like the "main gate" of your Spring Boot app. It sets everything up. |
| `@RestController`   | Tells Spring, "This class is used to handle web requests." |
| `@GetMapping` / `@PostMapping` / `@PutMapping` / `@DeleteMapping` | These say, "If someone calls this URL using GET/POST/etc., run this method." |
| `@Autowired`        | Automatically gives you an object to use—like saying "Hey Spring, give me a ready-made object!" |
| `@Service`          | Marks a class as a service – this is where your **logic** lives. |
| `@Repository`       | Marks a class that talks to the **database**. |
| `@Component`        | A generic annotation to tell Spring to manage this class. |

---

## ⚙️ Common Spring Boot Properties (`application.properties`)

| Property                                | What It Does                                              |
|-----------------------------------------|------------------------------------------------------------|
| `server.port=8081`                      | Runs the app on port 8081 instead of default 8080          |
| `spring.application.name=myapp`        | Names your Spring Boot application                        |
| `spring.datasource.url`                | JDBC URL for your database                                |
| `logging.level.org.springframework=DEBUG` | Shows detailed Spring logs in the console                |
| `management.endpoints.web.exposure.include=*` | Shows all Actuator endpoints (for monitoring)           |

---

## 🧪 Testing with Postman

You can test your REST API using **Postman** by sending HTTP requests (GET, POST, PUT, DELETE) to your controller endpoints like:

- `GET http://localhost:8080/hello`
- `POST http://localhost:8080/api/employees`

---

## 🧼 Summary

By the end of this course, you’ll be able to:

✅ Build and test Spring Boot REST APIs  
✅ Understand how Spring Boot and Maven work together  
✅ Inject configuration and secure your app  
✅ Monitor your app with Actuator  
✅ Deploy and run apps from the command line  

