# 🔐 Spring Custom API Gateway with JWT & Eureka

This project demonstrates a custom-built API Gateway in Spring Boot, using JWT-based authentication and role-based authorization. All microservices register with Eureka Server for dynamic service discovery.

---

## 👥 Users & Access Control

The application defines 3 users with specific access to services:

````json
{
  "user1": ["CREDIT", "DEBIT", "ADMIN"],
  "user2": ["CREDIT", "DEBIT"],
  "user3": ["ADMIN"]
}

---

## 🧩 Project Structure
spring-custom-gateway-poc/
 ├── api-gateway/ # Custom Gateway (JWT + Role-based filters)
 ├── auth/ # Auth service (Login, Issue JWT)
 ├── credit/ # Credit microservice
 ├── debit/ # Debit microservice
 ├── admin/ # Admin microservice
 ├── eureka-server/ # Eureka Server for Service Discovery


---

## ⚙️ Tech Stack

- **Spring Boot**
- **Spring Cloud Eureka**
- **JWT (JSON Web Tokens)**
- **Spring Security**
- **Custom Filters**
- **Maven**

---

## 🔐 Authentication Flow

1. User logs in via `/auth/login`
2. `auth` service generates a JWT with user role in claims
3. Request to other services goes through `api-gateway`
4. Gateway:
   - Verifies JWT (`JwtAuthFilter`)
   - Checks role for access (`RoleBasedAccessFilter`)
5. If valid → forwards to target service (via Eureka)

---

## 🚀 How to Run

1. **Start Eureka Server**
   ```bash
   cd eureka-server
   mvn spring-boot:run

2. **Start Auth Service**
   ```bash
   cd auth
   mvn spring-boot:run

3. **Start Other Microservices**
   Run each: admin, credit, debit

4. **Start API Gateway**
   ```bash
   cd api-gateway
   mvn spring-boot:run

Test Credentials
Use Postman or curl to log in:

json
Copy
Edit
POST /auth/token
{
  "username": "user1",
  "password": "password"
}


````
