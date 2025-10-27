# LMS API

**LMS (Learning Management System)** is a **RESTful API** designed to manage online learning processes.
It provides functionality for managing courses, users, quizzes, grading, and more.

The project is built with **Spring Boot 3.5**, **Java 21**, **MySQL**, **Cloudinary**, and **OpenAPI (Swagger UI)**.

## Requirements

* **Java 21+**
* **Maven 3.9+**
* **MySQL 8+**
* **Gmail SMTP server**
* **Cloudinary account**
* **RSA keys (PKCS#8)**

## Setup Instructions

1. **Clone the repository**

    ```bash
    git clone https://github.com/vitaliiromanko/lms-backend.git
    cd lms-backend
    ```

2. **Set environment variables**

    * `DB_URL`, `DB_USERNAME`, `DB_PWD` — MySQL database
    * `MAIL_USERNAME`, `MAIL_PWD`, `MAIL_SENDER` — Gmail SMTP credentials
    * `CLOUDINARY_CLOUD_NAME`, `CLOUDINARY_API_KEY`, `CLOUDINARY_API_SECRET` — Cloudinary credentials
    * `RSA_PUBLIC_KEY`, `RSA_PRIVATE_KEY` — RSA public/private keys

3. **Run the application**

    ```bash
   mvn spring-boot:run
    ```

    **API URL**: `http://localhost:8080/api/v1`

    > **NOTE**: The following features are enabled **only in the `dev` profile**:
    > * **Swagger UI**: `http://localhost:8080/api/v1/swagger-ui/index.html`
    > * **Automatic creation of database tables**
    > * **Test admin user**:
    >   * Email: `test-admin@tt.tt`
    >   * Password: `testT001`


## Main Features

* JWT-based authorization
* Email notifications via Gmail SMTP
* Cloudinary integration for media storage
* User management (students, instructors, administrators)
* Course structure management
* Quiz taking with automatic/manual grading
* Creation of reusable questions of different types
* Attachment images to questions
* WebSocket support for real-time tracking of time-limited quizzes
* OpenAPI (Swagger UI) documentation