# 🔄 Hibernate and JPA with Spring Boot – Full Guide

This guide explains how to integrate **Hibernate and JPA** into a **Spring Boot** application. You’ll learn how to set up the environment, map Java classes to database tables using annotations, perform CRUD operations, and configure Hibernate behavior.

---

## 📦 Tech Stack

- Java
- Spring Boot
- Hibernate
- JPA (Java Persistence API)
- MySQL
- Maven

---

## 🚀 Project Setup

### ✅ 1. Add Dependencies (in `pom.xml`)

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

### ✅ 2. Configure `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 🧩 JPA Entity Class Example

```java
import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    // Getters and setters
}
```

---

## 🧠 Important Annotations

| Annotation        | Description |
|-------------------|-------------|
| `@Entity`         | Marks a class as a JPA entity (maps to a DB table). |
| `@Table(name="")` | (Optional) Defines the table name. |
| `@Id`             | Marks the primary key field. |
| `@GeneratedValue` | Auto-generates the primary key. |
| `@Column(name="")`| Maps the field to a column in the DB table. |
| `@Repository`     | Indicates a class that interacts with the database (DAO or interface extending JpaRepository). |
| `@Autowired`      | Allows Spring to inject dependencies like `EntityManager` or repository instances. |

---

## 🔧 Spring Data JPA Repository

```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByName(String name);
}
```

---

## 🔍 Using EntityManager

Use `EntityManager` for advanced or custom queries.

### Example DAO Implementation with `EntityManager`

```java
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class StudentDaoImpl {

    private final EntityManager entityManager;

    // Spring injects EntityManager via constructor
    @Autowired
    public StudentDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Student> getAllStudents() {
        TypedQuery<Student> query = entityManager.createQuery("FROM Student", Student.class);
        return query.getResultList();
    }

    public void saveStudent(Student student) {
        entityManager.persist(student);
    }
}
```

### 🔎 Why `@Autowired` here?

Spring Boot uses **dependency injection** to manage beans. By adding `@Autowired` to the constructor, you're telling Spring to automatically inject a managed instance of `EntityManager` when creating the `StudentDaoImpl` bean.

---

## 📌 CRUD Operations

### ➕ Save Student

```java
Student student = new Student();
student.setName("John");
student.setAge(22);
studentRepo.save(student);
```

### 📖 Read Student

```java
Student student = studentRepo.findById(1).orElse(null);
```

### ✏️ Update Student

```java
student.setName("Updated Name");
studentRepo.save(student);
```

### ❌ Delete Student

```java
studentRepo.deleteById(1);
```

---

## ⚙️ ddl-auto Property Comparison

| Property Setting    | Description |
|---------------------|-------------|
| `none`              | No schema management. |
| `validate`          | Validates schema against entities. Fails on mismatch. |
| `update`            | Updates schema to match entity classes (non-destructive). |
| `create`            | Drops existing schema and creates new tables every run. |
| `create-drop`       | Same as `create`, but also drops schema when the app shuts down. |

---

## 📝 Summary

This guide covers:

- Setting up Hibernate with Spring Boot
- Creating and managing JPA entities
- Performing CRUD operations with `JpaRepository` and `EntityManager`
- Understanding important annotations
- Using the `spring.jpa.hibernate.ddl-auto` property

You’re now ready to build powerful Java apps with Spring Boot and Hibernate!

---

