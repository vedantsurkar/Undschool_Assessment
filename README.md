# 🌍 Global Class Offering Booking System

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue)
![Maven](https://img.shields.io/badge/Build-Maven-red)
![Status](https://img.shields.io/badge/Status-Completed-success)

A backend service for a global live-learning platform where teachers create course offerings with multiple sessions, and parents/students book complete offerings across different timezones.

This project focuses on **clean backend design**, **timezone-aware scheduling**, **booking conflict detection**, and **concurrency-safe booking logic**.

---

## 📌 Table of Contents

* [📖 Project Overview](#-project-overview)
* [✨ Key Features](#-key-features)
* [🛠 Tech Stack](#-tech-stack)
* [📁 Project Structure](#-project-structure)
* [🧠 Core Concepts](#-core-concepts)
* [🗄 Database Design](#-database-design)
* [🕒 Timezone Handling](#-timezone-handling)
* [🔐 Concurrency Handling](#-concurrency-handling)
* [🔁 Booking Conflict Detection](#-booking-conflict-detection)
* [🚀 API Endpoints](#-api-endpoints)
* [⚙️ Setup Instructions](#️-setup-instructions)
* [🧪 Sample Data](#-sample-data)
* [⚠️ Example Conflict Scenario](#️-example-conflict-scenario)
* [🏗 Engineering Approach](#-engineering-approach)
* [🚧 Future Improvements](#-future-improvements)
* [👨‍💻 Author](#-author)

---

## 📖 Project Overview

The **Global Class Offering Booking System** is a simplified backend service for an online learning platform.

Teachers can create course offerings such as weekly batches or summer camps. Each offering contains multiple sessions. Parents can view available offerings in their own local timezone and book an entire offering.

The system ensures that a parent cannot book two offerings if **any session timing overlaps** with an already booked offering.

---

## ✨ Key Features

### 👨‍🏫 Teacher Features

* Create a new course offering
* Add multiple sessions to an offering
* View teacher-specific offerings
* Store session timings in UTC
* Accept teacher-created timings in the teacher's timezone

### 👨‍👩‍👧 Parent / Student Features

* View all available offerings
* View session timings in the parent’s local timezone
* Book an entire offering
* View booked offerings
* Prevent overlapping bookings
* Handle simultaneous booking attempts safely

---

## 🛠 Tech Stack

| Category       | Technology                 |
| -------------- | -------------------------- |
| ☕ Language     | Java 21                    |
| 🚀 Framework   | Spring Boot 3.2.5          |
| 🌐 Web Layer   | Spring Web                 |
| 🧩 Persistence | Spring Data JPA, Hibernate |
| 🗄 Database    | MySQL 8                    |
| 📦 Build Tool  | Maven                      |
| 🔗 ORM API     | Jakarta Persistence API    |

---

## 📁 Project Structure

```text
Undoschool_Assessment/
│
├── .gitignore
├── README.md
├── sample-data.sql
├── pom.xml
├── mvnw
├── mvnw.cmd
│
├── .mvn/
│   └── wrapper/
│       ├── maven-wrapper.properties
│       └── maven-wrapper.jar
│
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── example/
    │   │           └── Undoschool_Assessment/
    │   │               ├── UndoschoolAssessmentApplication.java
    │   │               │
    │   │               ├── entity/
    │   │               │   ├── Teacher.java
    │   │               │   ├── Course.java
    │   │               │   ├── Offering.java
    │   │               │   ├── Session.java
    │   │               │   ├── Parent.java
    │   │               │   └── Booking.java
    │   │               │
    │   │               ├── repository/
    │   │               │   ├── TeacherRepository.java
    │   │               │   ├── CourseRepository.java
    │   │               │   ├── OfferingRepository.java
    │   │               │   ├── SessionRepository.java
    │   │               │   ├── ParentRepository.java
    │   │               │   └── BookingRepository.java
    │   │               │
    │   │               ├── controller/
    │   │               │   ├── TeacherController.java
    │   │               │   └── ParentController.java
    │   │               │
    │   │               ├── util/
    │   │               │   └── TimezoneHelper.java
    │   │               │
    │   │               └── exception/
    │   │                   └── GlobalExceptionHandler.java
    │   │
    │   └── resources/
    │       └── application.properties
    │
    └── test/
        └── optional
```

---

## 🧠 Core Concepts

### 📚 1. Course

A course represents the main class category.

Examples:

* Python Coding
* Art Drawing
* Public Speaking

---

### 🗓 2. Offering

An offering is a schedulable version or section of a course.

Examples:

* Saturday Batch
* Weekday Summer Camp
* Evening Batch

Each offering belongs to one teacher and one course.

---

### ⏰ 3. Session

A session represents one actual class timing inside an offering.

Example:

```text
Offering: Python Coding - Saturday Batch

Sessions:
June 7  -> 6 PM to 7 PM
June 14 -> 6 PM to 7 PM
June 21 -> 6 PM to 7 PM
```

All session times are stored in UTC.

---

### 👨‍👩‍👧 4. Parent

A parent/student can view and book offerings.

Each parent has a timezone, such as:

* Asia/Kolkata
* Europe/London
* America/Los_Angeles

---

### 🎟 5. Booking

A booking is created when a parent books an offering.

Bookings happen at the **offering level**, not the individual session level.

This means when a parent books an offering, all sessions inside that offering are booked together.

---

## 🗄 Database Design

The project uses the following main tables:

| Table      | Purpose                                    |
| ---------- | ------------------------------------------ |
| `teacher`  | Stores teacher details                     |
| `course`   | Stores course/class details                |
| `offering` | Stores course offerings/sections           |
| `session`  | Stores session timings in UTC              |
| `parent`   | Stores parent/student details and timezone |
| `booking`  | Stores parent bookings                     |

---

## 🔗 Entity Relationships

```text
Teacher 1 ──── * Offering
Course  1 ──── * Offering
Offering 1 ─── * Session
Parent  1 ──── * Booking
Offering 1 ─── * Booking
```

---

## ✅ Booking Constraint

The `booking` table has a unique constraint on:

```text
parent_id + offering_id
```

This prevents the same parent from booking the same offering multiple times.

---

## 🕒 Timezone Handling

Timezone handling is one of the most important parts of this project.

### 👨‍🏫 Teacher Side

Teachers create sessions in their own timezone.

Example:

```text
Teacher timezone: America/New_York
Teacher enters: 2025-06-07T18:00:00
```

Before storing, the system converts this time to UTC.

---

### 🗄 Database Side

All session timings are stored in UTC.

Example:

```text
Stored UTC time: 2025-06-07 22:00:00
```

---

### 👨‍👩‍👧 Parent Side

Parents view session timings in their own local timezone.

Example:

```text
Parent timezone: Asia/Kolkata
Stored UTC time: 2025-06-07 22:00:00
Displayed time: 2025-06-08 03:30
```

The `TimezoneHelper` class is responsible for:

* Converting teacher local time to UTC
* Converting UTC time to parent local time

---

## 🔐 Concurrency Handling

The system uses **pessimistic locking** while booking an offering.

When a parent tries to book an offering:

1. The parent record is locked.
2. Existing bookings of the parent are fetched.
3. Sessions of the new offering are fetched.
4. The system checks for overlapping sessions.
5. If a conflict exists, the booking is rejected.
6. If no conflict exists, the booking is saved.

This prevents invalid bookings during simultaneous booking requests.

---

## 🔁 Booking Conflict Detection

A parent cannot book another offering if any session overlaps with an already booked session.

Two sessions are considered overlapping when:

```text
newSession.start < existingSession.end
AND
newSession.end > existingSession.start
```

In the code, this logic is used to compare sessions before creating a booking.

---

# 🚀 API Endpoints

Base URL:

```text
http://localhost:8080
```

---

## 👨‍🏫 Teacher APIs

### 1️⃣ Create Offering

Creates a new offering and adds sessions to it.

```http
POST /api/teachers/{teacherId}/offerings
```

#### Request Body

```json
{
  "courseId": 1,
  "name": "Saturday Batch",
  "teacherTimezone": "America/New_York",
  "sessions": [
    {
      "startTime": "2025-06-07T18:00:00",
      "endTime": "2025-06-07T19:00:00"
    },
    {
      "startTime": "2025-06-14T18:00:00",
      "endTime": "2025-06-14T19:00:00"
    }
  ]
}
```

#### Success Response

```json
{
  "id": 1,
  "name": "Saturday Batch",
  "message": "Offering created with sessions"
}
```

---

### 2️⃣ Add Sessions to Offering

Adds additional sessions to an existing offering.

```http
POST /api/teachers/offerings/{offeringId}/sessions
```

#### Request Body

```json
{
  "sessions": [
    {
      "startTime": "2025-06-21T18:00:00",
      "endTime": "2025-06-21T19:00:00"
    }
  ]
}
```

#### Success Response

```text
Sessions added successfully
```

---

### 3️⃣ Get Teacher Offerings

Fetches all offerings created by a teacher.

```http
GET /api/teachers/{teacherId}/offerings
```

#### Example Response

```json
[
  {
    "id": 1,
    "name": "Saturday Batch",
    "sessions": [
      {
        "startUtc": "2025-06-07T22:00",
        "endUtc": "2025-06-07T23:00"
      },
      {
        "startUtc": "2025-06-14T22:00",
        "endUtc": "2025-06-14T23:00"
      }
    ]
  }
]
```

---

## 👨‍👩‍👧 Parent APIs

### 1️⃣ Get Available Offerings

Fetches all available offerings and displays session times in the parent’s local timezone.

```http
GET /api/parents/{parentId}/offerings
```

#### Example Response

```json
[
  {
    "id": 1,
    "name": "Saturday Batch",
    "sessions": [
      {
        "start": "2025-06-08 03:30",
        "end": "2025-06-08 04:30"
      }
    ]
  }
]
```

---

### 2️⃣ Book Offering

Books an entire offering for a parent.

```http
POST /api/parents/{parentId}/bookings
```

#### Request Body

```json
{
  "offeringId": 1
}
```

#### Success Response

```text
Booking successful
```

#### Conflict Response

```text
Cannot book: session conflicts with existing booking
```

---

### 3️⃣ Get Parent Bookings

Fetches all offerings booked by a parent.

```http
GET /api/parents/{parentId}/bookings
```

#### Example Response

```json
[
  {
    "offeringId": 1,
    "offeringName": "Saturday Batch",
    "sessions": [
      {
        "start": "2025-06-08 03:30",
        "end": "2025-06-08 04:30"
      }
    ],
    "bookedAt": "2025-06-01T10:15:30"
  }
]
```

---

## ⚙️ Setup Instructions

### ✅ 1. Prerequisites

Install the following:

* Java 21
* Maven
* MySQL 8
* Git
* Postman or any API testing tool

---

### 📥 2. Clone the Repository

```bash
git clone <your-repository-url>
cd Undoschool_Assessment
```

---

### 🗄 3. Create MySQL Database

Login to MySQL and create the database:

```sql
CREATE DATABASE global_school;
```

---

### 🔧 4. Configure Database Connection

Open:

```text
src/main/resources/application.properties
```

Update it as follows:

```properties
spring.application.name=Undoschool_Assessment

spring.datasource.url=jdbc:mysql://localhost:3306/global_school?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_mysql_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

spring.jpa.properties.hibernate.jdbc.time_zone=UTC
```

> ⚠️ **Important:** Do not commit your real database password to GitHub.

---

### ▶️ 5. Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

For Windows:

```bash
mvnw.cmd spring-boot:run
```

The application will start at:

```text
http://localhost:8080
```

---

## 🧪 Sample Data

The project includes a `sample-data.sql` file.

It contains sample records for:

* Teachers
* Courses
* Parents
* Offerings
* Sessions
* Bookings

Run this file after creating the database and starting the application once, so Hibernate can create the tables.

```bash
mysql -u root -p global_school < sample-data.sql
```

You can also run the file manually using MySQL Workbench.

---

## 📊 Sample Data Overview

### 👨‍🏫 Teachers

```text
Ms. Sarah Smith
Mr. John Davis
```

### 📚 Courses

```text
Python Coding
Art Drawing
Public Speaking
```

### 👨‍👩‍👧 Parents

```text
Alice Johnson - Asia/Kolkata
Bob Williams - Europe/London
Carol Martinez - America/Los_Angeles
```

### 🗓 Offerings

```text
Saturday Batch
Weekday Summer Camp
Evening Batch
```

---

## ⚠️ Example Conflict Scenario

Assume Alice books:

```text
Python Coding - Saturday Batch

Session 1: June 7, 2025 22:00 UTC to 23:00 UTC
Session 2: June 14, 2025 22:00 UTC to 23:00 UTC
Session 3: June 21, 2025 22:00 UTC to 23:00 UTC
```

Now, if Alice tries to book another offering that has any session overlapping with these timings, the system rejects the booking.

Example response:

```text
Cannot book: session conflicts with existing booking
```

This ensures that a parent cannot book two classes at the same time.

---

## 🧯 Error Handling

The application includes a global exception handler for consistent error responses.

Examples:

```text
Parent not found
Teacher not found
Course not found
Offering not found
Cannot book: session conflicts with existing booking
```

Unexpected errors are handled using a generic error response.

---

## 🏗 Engineering Approach

This project follows a clean and simple backend design.

Important engineering decisions:

* Store all session times in UTC
* Convert timezone only at API boundaries
* Use offering-level booking
* Use pessimistic locking for concurrent booking safety
* Use database-level uniqueness to prevent duplicate bookings
* Keep entity, repository, controller, utility, and exception layers separate
* Return simplified API responses to avoid circular JSON serialization issues

---

## ✅ Assignment Requirement Coverage

| Requirement                         | Status        |
| ----------------------------------- | ------------- |
| Teacher can create offering         | ✅ Implemented |
| Teacher can add sessions            | ✅ Implemented |
| Teacher can view offerings          | ✅ Implemented |
| Parent can view available offerings | ✅ Implemented |
| Parent can book offering            | ✅ Implemented |
| Parent can view bookings            | ✅ Implemented |
| Booking happens at offering level   | ✅ Implemented |
| Time conflict detection             | ✅ Implemented |
| Concurrent booking handling         | ✅ Implemented |
| Timezone conversion                 | ✅ Implemented |
| MySQL database design               | ✅ Implemented |
| Error handling                      | ✅ Implemented |

---

## 📝 Assumptions

* Authentication and authorization are not implemented because the assignment focuses on booking workflow and backend logic.
* Teachers, courses, and parents are preloaded using sample data.
* Parents book an entire offering, not individual sessions.
* All session times are stored in UTC.
* Timezones are expected to be valid IANA timezone values such as `Asia/Kolkata`, `Europe/London`, and `America/New_York`.

---

## 🚧 Future Improvements

* Add Spring Security authentication and role-based authorization
* Add DTO classes instead of inner request classes
* Add service layer for better separation of business logic
* Add request validation using Bean Validation
* Add custom exception classes
* Add Swagger/OpenAPI documentation
* Add unit and integration tests
* Add pagination and filtering for offerings
* Add Docker support for MySQL and Spring Boot
* Add teacher availability validation
* Add booking cancellation feature

---

## 👨‍💻 Author

**Vedant Surkar**

Backend Engineering Assignment
Global Class Offering Booking System

---

## 📄 License

This project is created as part of a backend engineering assignment.
