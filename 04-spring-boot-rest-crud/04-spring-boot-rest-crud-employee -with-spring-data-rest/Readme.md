# 🌟 Spring Boot + Spring Data REST CRUD API

This project shows how to build a complete **RESTful API** for managing employees — **without writing any controller or service code!**

Thanks to **Spring Data REST**, we just define a `JPA Entity` and a `JpaRepository`, and Spring automatically exposes the API endpoints.

---

## ✅ What's New in This Version?

| Old Setup                          | New Setup (Spring Data REST)         |
|-----------------------------------|--------------------------------------|
| Controller Class (REST API)       | ❌ Removed                           |
| Service Class (Business Logic)    | ❌ Removed                           |
| Manual CRUD method mapping        | ❌ Not needed                        |
| REST endpoints auto-created       | ✅ By Spring Data REST               |
| Less code, same functionality     | ✅ Yes!                              |

---

## 🧱 Project Structure

```
com.springboot.crudDemo
│
├── entity          // Employee entity
├── dao             // EmployeeRepository interface
```

---

## 🧠 How It Works (In Simple Words)

- **Spring Data REST** looks at your `JpaRepository` and **automatically creates** all the CRUD REST APIs for it.
- It uses the **Java class name (`Employee`) and pluralizes it** to create endpoints like `/employees`.
- No controller or service logic is required — Spring handles it for you!

---

## ⚙️ Config File (`application.properties`)

```properties
# All REST endpoints will start with this path
spring.data.rest.base-path=/magic-api
```

---

## 🧪 Example Endpoints

| Operation       | HTTP Method | Endpoint                   |
|----------------|-------------|----------------------------|
| Get all        | GET         | `/magic-api/employees`     |
| Get one        | GET         | `/magic-api/employees/{id}`|
| Add new        | POST        | `/magic-api/employees`     |
| Update         | PUT/PATCH   | `/magic-api/employees/{id}`|
| Delete         | DELETE      | `/magic-api/employees/{id}`|

---

## 🧾 Repository Layer

### `EmployeeRepository.java`

```java
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // No code needed, Spring Data REST takes care of everything!
}
```

---

## 🧍 Employee Entity

```java
@Entity
@Table(name = "employee")
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

## 🪄 Magic Behind the Scenes

Spring Boot + Spring Data REST:
- Reads your entity (like `Employee`)
- Looks at your repository (`EmployeeRepository`)
- Auto-creates endpoints like `/magic-api/employees`
- Returns data as JSON
- Handles add, update, delete, and find operations

---

## 🔗 What is HATEOAS?

**HATEOAS** stands for **Hypermedia as the Engine of Application State**.

In simple terms:
> Spring Data REST automatically adds useful **links inside your JSON responses**, so the client knows what actions it can take next.

### Example Response:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "_links": {
    "self": {
      "href": "http://localhost:8080/magic-api/employees/1"
    },
    "employee": {
      "href": "http://localhost:8080/magic-api/employees/1"
    }
  }
}
```

These links make it easy for frontend apps or API consumers to **navigate** and **interact** with the API dynamically — without hardcoding URLs.

### Benefits:
- 📎 Self-descriptive API
- 🔁 Easy navigation from one resource to another
- 🤖 Helps clients understand next steps (edit, delete, view more)

---

## 🚀 How to Run

1. Add the required dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>
```

2. Set the base path in `application.properties`:

```properties
spring.data.rest.base-path=/magic-api
```

3. Run your Spring Boot app.
4. Open Postman or browser and test endpoints like:

- `GET http://localhost:8080/magic-api/employees`
- `POST http://localhost:8080/magic-api/employees`
- `DELETE http://localhost:8080/magic-api/employees/1`

---



## ➕ Added Pagination and Sorting

Spring Data REST makes it super easy to use **pagination** and **sorting** via **query parameters** — no extra code needed!

### ✅ Configuration

```properties
spring.data.rest.base-path=/magic-api
spring.data.rest.default-page-size=3
```

- The default page size is set to **3**, but you can customize it per request.
- You also renamed the endpoint to `/members` using the annotation below.

### 🧾 Updated Repository

```java
@RepositoryRestResource(path = "members")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // Spring Data REST takes care of everything!
}
```

---

## 🔁 Pagination API Examples

| Operation               | Example URL                                                 |
|------------------------|-------------------------------------------------------------|
| Get first page (3 items) | `GET /magic-api/members`                                   |
| Get page 2              | `GET /magic-api/members?page=1`                             |
| Change page size        | `GET /magic-api/members?size=5`                             |
| Go to page 3, size 2    | `GET /magic-api/members?page=2&size=2`                      |

> ✅ Page numbers start at 0 (i.e., `page=0` is the first page)

---

## 🔃 Sorting API Examples

| Operation                  | Example URL                                                   |
|---------------------------|---------------------------------------------------------------|
| Sort by `firstName` ASC   | `GET /magic-api/members?sort=firstName`                       |
| Sort by `lastName` DESC   | `GET /magic-api/members?sort=lastName,desc`                   |
| Multiple sort fields      | `GET /magic-api/members?sort=lastName,asc&sort=firstName,desc`|

---

## 🔄 Combine Paging + Sorting

| Operation                             | Example URL                                                            |
|--------------------------------------|------------------------------------------------------------------------|
| Page 1, 3 items per page, sort by ID | `GET /magic-api/members?page=0&size=3&sort=id,asc`                     |
| Page 2, 5 items per page, sort name  | `GET /magic-api/members?page=1&size=5&sort=firstName,desc`             |

---

## 📌 Notes

- Sorting only works on valid **field names** from the `Employee` entity.
- Sorting and paging can be combined as needed using query parameters.
- Spring Data REST automatically includes paging metadata like:

```json
"page": {
  "size": 3,
  "totalElements": 12,
  "totalPages": 4,
  "number": 0
}
```

- This helps front-end developers build UI pagination controls easily.

---
## ✅ Benefits of Spring Data REST

- ⚡ Super-fast development
- 🧼 Clean code (no boilerplate)
- 🔗 Auto HATEOAS support
- 🔁 Built-in paging, sorting, filtering
- 🧠 Great for prototypes or quick APIs

---
