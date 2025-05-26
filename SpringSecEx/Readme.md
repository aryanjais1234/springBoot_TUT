# 🌐 JWT Authentication and Security in a Spring Boot Application

Welcome to a complete, beginner-friendly guide to implementing **JWT (JSON Web Token)** authentication in a Spring Boot application. This README is designed to give you a clear understanding of how JWTs work, how to integrate them into your Spring Boot project, and how each component fits into the overall security flow.

---

## 📌 Overview

**JWT Authentication** is a powerful way to secure REST APIs. Instead of maintaining session state on the server, a token is generated after a user logs in and is sent with each subsequent request, allowing stateless authentication.

This project demonstrates:

* How to generate and validate JWTs.
* How to secure endpoints using Spring Security.
* How to build a secure authentication system using standard Spring Boot practices.

---

## 📁 Project Structure

Below is the simplified structure of the project for easy navigation:

```
src/main/java/com/spring/SpringSecEx
├── config
│   └── JwtFilter.java              // Filter to handle JWT validation
│   └── SecurityConfig.java         // Spring Security configuration
├── controller
│   └── AuthController.java         // Handles authentication and token generation
├── model
│   └── Users.java                  // Represents a user in the system
│   └── UserPrincipal.java          // Implements Spring Security's UserDetails
├── repo
│   └── UserRepo.java               // JPA Repository to interact with the database
├── service
│   └── JWTService.java             // Generates and validates JWT tokens
│   └── MyUserDetailsService.java   // Custom UserDetailsService implementation
```

---

## 🔐 Flow of Authentication

### 🔄 Basic JWT Authentication Flow:

1. **Login Request**
   The user submits credentials (username and password) via the `/login` endpoint.

2. **Token Generation**
   If credentials are valid, the backend generates a JWT token and returns it in the response.

3. **Token Usage**
   The client stores the token (usually in localStorage) and sends it in the `Authorization` header (`Bearer <token>`) with each subsequent request.

4. **Token Validation**
   For each incoming request, the server checks the validity of the token using the JWT filter. If valid, the request is processed and authorized accordingly.

---

## 🧠 Core Concepts

### ✅ What is JWT?

A JWT is a compact, URL-safe token made up of three parts:

1. **Header**
   Metadata including the signing algorithm (e.g., HS256).

2. **Payload**
   Data (claims) such as username (`sub`), expiration time (`exp`), etc.

3. **Signature**
   A secure signature created using a secret key to ensure data integrity.

#### 🔍 Sample Token

```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraWxsIiwiaWF0IjoxNzg2MDU1ODAwLCJleHAiOjE3ODYwNTYxMDB9.gGq1kOb8UOsHdLdmFVH8zQGg
```

---

## 🧩 Key Annotations (Explained Simply)

* `@Service`: Marks the class as a service layer component.
* `@Component`: Generic stereotype annotation used for beans like filters.
* `@Autowired`: Injects dependent beans automatically.
* `@Override`: Indicates method overrides parent/interface methods.
* `@Repository`: Specifies the class as a data access layer bean.
* `@RestController`: Combines `@Controller` and `@ResponseBody`.
* `@RequestMapping`: Maps web requests to controller classes or methods.

---

## 🔍 In-Depth Explanation of Components

### 1️⃣ **JWTService (Token Manager)**

Handles:

* Token **creation** during login.
* Token **validation** on each request.
* **Claim extraction** like username and expiry from the token.

#### Core Methods:

* `generateToken(String username)`
* `validateToken(String token)`
* `getClaim(String token)`
* `getSigningKey()`

---

### 2️⃣ **MyUserDetailsService (Custom UserDetails Provider)**

Bridges your user model (`Users`) with Spring Security by implementing `UserDetailsService`.

#### Core Method:

* `loadUserByUsername(String username)`
  Looks up the user in the DB and returns a `UserDetails` object (usually `UserPrincipal`).

---

### 3️⃣ **SecurityConfig (Spring Security Setup)**

Configures:

* Public and protected endpoints.
* Authentication entry points.
* Custom JWT Filter in the request filter chain.

#### Key Responsibilities:

* Allow unrestricted access to `/login`, `/register`.
* Secure all other endpoints.
* Register the JWT filter **before** `UsernamePasswordAuthenticationFilter`.

---

### 4️⃣ **JwtFilter (Token Checker)**

* Intercepts each HTTP request.
* Checks for the presence of the `Authorization` header.
* Validates the JWT using `JWTService`.
* Sets the authentication object in `SecurityContextHolder`.

> 💡 Think of it as a gatekeeper that ensures only valid requests proceed further.

---

### 5️⃣ **AuthController (Login Endpoint)**

Manages the authentication API logic:

* Accepts login requests.
* Authenticates users.
* Returns a signed JWT upon successful login.

```java
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody AuthRequest request) {
    final UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
    if (passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
        String token = jwtService.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
}
```

---

## 🧰 How to Implement JWT in Spring Boot (Step-by-Step)

### ✅ Step 1: Add Required Dependencies

Update your `pom.xml`:

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
   <groupId>io.jsonwebtoken</groupId>
   <artifactId>jjwt-api</artifactId>
   <version>0.12.3</version>
</dependency>
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
   <groupId>org.springframework.h2</groupId>
   <artifactId>h2</artifactId>
</dependency>
```

---

### ✅ Step 2: Create a `Users` Entity

Define your user model with fields like `username`, `password`, and `roles`.

---

### ✅ Step 3: Generate Tokens on Login

* Authenticate the user using the `UserDetailsService`.
* Generate a token using `JWTService`.
* Return the token to the client.

---

### ✅ Step 4: Intercept Requests with JwtFilter

* Check the `Authorization` header.
* If valid, extract username and set authentication in `SecurityContext`.

---

### ✅ Step 5: Secure Endpoints

In `SecurityConfig`, specify:

* Which endpoints are public (`/login`, `/register`).
* Which endpoints require authentication (`/user/**`, etc.).
* Register the custom filter before the standard auth filter.

---

## 📡 Sample API Endpoints

| HTTP Method | Endpoint        | Description                                 |
| ----------- | --------------- | ------------------------------------------- |
| `POST`      | `/login`        | Authenticates user and returns a JWT token. |
| `GET`       | `/user/profile` | Retrieves profile of the logged-in user.    |

---

## 🛡️ Best Practices for JWT Security

1. **Secure the Secret Key**
   Use environment variables or a secure vault. Never hardcode in production.

2. **Use HTTPS**
   Prevents MITM attacks and protects tokens in transit.

3. **Token Expiry & Refresh**
   Keep token expiry short (e.g., 15 mins) and implement a refresh mechanism for prolonged sessions.

4. **Role-Based Access Control (RBAC)**
   Use roles and authorities to protect sensitive endpoints.

5. **Blacklist/Invalidate Tokens (Advanced)**
   Store tokens in Redis or DB for blacklisting after logout or expiry.

---

## 🤝 Need Help?

If you have any questions, issues, or suggestions, feel free to open an issue or start a discussion. This guide is built to help developers secure their APIs using Spring Boot and JWT the right way.

---

Happy Coding! 🚀
**Secure your APIs with confidence using JWT + Spring Boot.**

---
