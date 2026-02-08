Was created as part of [roadmap](https://zhukovsd.github.io/java-backend-learning-course/).
# 🌦️ Weather Tracker (Spring MVC, non-Boot)

A classic **Spring MVC (non-Spring Boot)** web application for searching, saving, and managing weather locations per user.  
The project demonstrates clean layered architecture, manual configuration, session-based authentication, Liquibase migrations, and Thymeleaf UI rendering.

---

## ✨ Features

- 🔐 **Custom authentication & session management**
    - Manual session lifecycle
    - Cookie-based auth token
    - Session expiration & cleanup job
- 🌍 **Weather search by location**
    - External weather API integration
    - Location suggestions
- ⭐ **Saved locations per user**
    - User-scoped uniqueness (lat + lon)
- 🧱 **Layered architecture**
    - Controller → Service → Repository
    - DTO / Entity separation
- 🧬 **Liquibase database migrations**
- 🧪 **Integration & unit testing**
    - H2 for tests
- 🎨 **Thymeleaf + Bootstrap UI**
- 📦 **WAR packaging for Apache Tomcat**

---

## 🛠️ Tech Stack

| Layer | Technology                    |
|-----|-------------------------------|
| Language | Java 25                       |
| Web | Spring MVC (non-Boot)         |
| View | Thymeleaf, Bootstrap          |
| Persistence | JPA (Hibernate)               |
| DB Migrations | Liquibase                     |
| Build | Maven                         |
| Packaging | WAR                           |
| Servlet Container | Apache Tomcat                 |
| Database | PostgreSQL (prod), H2 (tests) |

---

## 🚀 Build Guide
- Java 25
- Maven
- PostgreSQL
- Apache Tomcat 
- Min required VM options:
```
-Dspring.profiles.active=dev
-DDB_URL=jdbc:postgresql:_your link to DB_
-DDB_USERNAME=_your_db_username_
-DDB_PASSWORD=_your_db_password_
-DAPI_KEY=_your_api_key_
-DAPI_URL=https://api.openweathermap.org
```

