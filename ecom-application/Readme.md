# 🛍️ E-Commerce Spring Boot Application

## 📌 Project Description

This is a beginner-friendly E-Commerce backend built using **Java**, **Spring Boot**, **Spring Data JPA**, and **Hibernate**, designed to demonstrate real-world application of RESTful APIs, relational database mapping, and Java 8 Stream API.

It covers the complete workflow of an e-commerce system, including:

1. User Registration
2. Product Management
3. Cart Functionality
4. Order Placement

This project also demonstrates how to use Java 8 features, entity relationships, DTO mapping, and clean code practices.

---

## 🚀 Workflow Overview

### 1️⃣ User Registration

* Users can register using `POST /api/users`
* Each user has a one-to-one relationship with an **Address**

### 2️⃣ Product Management (Admin)

* Admin can add products using `POST /api/products`
* Products are stored in the database with details like name, price, category, stock quantity, and image URL

### 3️⃣ Add to Cart

* Users can add products to their cart using `POST /api/cart`
* A `CartItem` is created with quantity, price, and linked **Product** and **User**
* CartItems are updated if the product already exists in the cart

### 4️⃣ View Cart

* Users can view their cart with `GET /api/cart`
* Cart is fetched using user ID

### 5️⃣ Place Order

* Orders are placed using `POST /api/orders`
* `Order` entity contains multiple `OrderItem`s created from cart items
* The cart is cleared after order placement

---

## 🗂️ Project Structure

```plaintext
com.ecom.app
│
├── Controller        // REST Controllers
├── Service           // Business Logic
├── Repository        // Spring Data JPA Interfaces
├── Models            // Entity Classes
├── dto               // Data Transfer Objects
└── Application.java  // Main Spring Boot class
```

---

## 📦 Entity Relationships & Rationale

```plaintext
User (1) ----- (1) Address
User (1) ----- (M) CartItem
User (1) ----- (1) Order

Product (1) ----- (M) CartItem
Product (1) ----- (M) OrderItem

Order (1) ----- (M) OrderItem
```

### 🔍 Explanation of Relationships

#### ✅ User → Address (`@OneToOne`)

* A **User** has exactly one **Address**, and vice versa.
* Mapped via `@OneToOne(cascade = ALL)` for automatic persistence of address when saving a user.

#### ✅ User → CartItem (`@OneToMany`)

* One user can add **multiple items to the cart**.
* Each `CartItem` is associated with one user only → hence `@ManyToOne` on the `CartItem` side.

#### ✅ Product → CartItem (`@OneToMany`)

* A product can be present in multiple users' carts.
* So, multiple `CartItem`s can point to the same `Product` → hence `@ManyToOne`.

#### ✅ Order → OrderItem (`@OneToMany`)

* Each order will contain multiple items → one `Order` to many `OrderItem`s.
* `OrderItem` uses `@ManyToOne` to refer back to the `Order`.

#### ✅ Product → OrderItem (`@OneToMany`)

* A product can be purchased multiple times in different orders.
* Each `OrderItem` links to a single product.

#### ✅ User → Order (`@OneToOne` or `@OneToMany` — simplified as one active order per user)

* Each order belongs to one user.
* Order is linked via `@OneToOne` in this project structure.

---

## 🧠 Java 8 Stream API Usage

### 🛒 CartService → `getAllProducts()`

```java
cartItemRepository.findByUser(user).stream()
    .map(item -> new CartItemDTO(...))
    .collect(Collectors.toList());
```

✅ Used to map `CartItem` entity list to a list of `CartItemDTO`s using `stream()`, `map()`, and `collect()`.

---

### 📦 OrderService → `createOrder()`

```java
BigDecimal totalPrice = cartItems.stream()
    .map(CartItem::getPrice)
    .reduce(BigDecimal.ZERO, BigDecimal::add);
```

✅ `map()` to extract price, then `reduce()` to sum all prices.

```java
List<OrderItem> orderItems = cartItems.stream()
    .map(item -> new OrderItem(...))
    .collect(Collectors.toList());
```

✅ Stream used to convert `CartItem` list to `OrderItem` list.

```java
order.getItems().stream()
    .map(item-> new OrderItemDTO(...))
    .collect(Collectors.toList());
```

✅ Converts saved order's items to response DTOs.

---

## 🏗️ Architecture Diagram

```plaintext
   ┌───────────────┐
   │   Controller  │   <-- REST APIs
   └──────┬────────┘
          │
          ▼
   ┌───────────────┐
   │    Service    │   <-- Business logic, transactions
   └──────┬────────┘
          │
          ▼
   ┌───────────────┐
   │   Repository  │   <-- Data access using JPA
   └──────┬────────┘
          │
          ▼
   ┌───────────────┐
   │   Database    │   <-- MySQL or H2
   └───────────────┘
```

**DTOs** sit between **Controller** and **Service** to ensure abstraction and clean response/request mapping.

---

## 🔧 Tech Stack Used

* **Java 17**
* **Spring Boot**
* **Spring Data JPA**
* **Hibernate**
* **MySQL** (or any relational DB)
* **Lombok** for boilerplate code reduction

---

## 📚 Learning Highlights

* ✅ CRUD operations using Spring Boot
* ✅ Entity relationships and annotations (`@OneToOne`, `@OneToMany`, `@ManyToOne`)
* ✅ DTO pattern to abstract internal structures
* ✅ Java 8 Stream API to transform and manipulate data
* ✅ Transaction management using `@Transactional`
* ✅ REST API standards (status codes, request mappings)

---

## 🧪 Testing Endpoints (with Postman or curl)

| Endpoint        | Method | Description           |
| --------------- | ------ | --------------------- |
| `/api/users`    | POST   | Create new user       |
| `/api/products` | POST   | Add new product       |
| `/api/cart`     | POST   | Add product to cart   |
| `/api/cart`     | GET    | View user's cart      |
| `/api/orders`   | POST   | Place order from cart |

---

## 📝 Final Notes

This project is perfect for:

* 🎓 Students learning Spring Boot and JPA
* 👨‍💻 Developers understanding real-world REST APIs
* 🔁 Practicing Java 8 Streams and DTO Mapping

Feel free to extend this with authentication (JWT), pagination, filtering, or a front-end integration! ✨

---

## 📬 Contributing

Pull requests and suggestions are welcome. Let’s build something amazing! 💪

---

## 👨‍🔧 Author

**Aryan Jaiswal**

> Happy Coding! 😊
# e-Commerce_Spring_Boot_Application
# e-Commerce_Spring_Boot_Application
