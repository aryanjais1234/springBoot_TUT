# Lazy Initialization

---
Lazy initialization is a powerful technique in Spring Boot that delays bean creation until they’re first needed, offering faster application startup times. However, this optimization comes with trade-offs that can impact runtime performance.

### What is Lazy Initialization?
By default, Spring Boot initializes all beans at startup (eager initialization). 

#### With lazy initialization:

---
Beans are created on first use (e.g., during an HTTP request).
Startup time decreases because fewer beans are initialized upfront.
Runtime latency may increase for the first request that triggers initialization.

### Configuration Options
1. Global Lazy Initialization
   Enable for all beans via application.properties:
```
spring.main.lazy-initialization=true
```
Impact: 

Startup Time: Reduced by 30–50% in apps with many beans.
First Request: May take 2–3x longer (see benchmarks below).

### Advantages:
- Only create object as needed
- May help with faster startup time if you have large number of components

### Disadvantages:
- If you have web related components like @RestController, not created until requested
- May not discover configuration issue until too late
- Need to make sure you have enough memory for all beans once created