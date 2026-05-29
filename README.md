# 🌍 Global Class Offering Booking System

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue)
![Maven](https://img.shields.io/badge/Build-Maven-red)
![Status](https://img.shields.io/badge/Status-Completed-success)

A backend service for a global live-learning platform where teachers create course offerings with multiple sessions and parents/students book complete offerings across different timezones.

The system focuses on **timezone-aware scheduling**, **offering-level booking**, **booking conflict detection**, and **concurrency-safe booking handling**.

---

## 📌 Project Overview

This project implements a simplified backend system for a global online learning platform.

Teachers can create offerings for courses such as weekly batches or summer camps. Each offering contains multiple sessions. Parents can view available offerings in their own local timezone and book the entire offering.

The system ensures that once a parent books an offering, all session timings belonging to that offering are locked for that parent. The parent cannot book another offering if any session overlaps with already booked sessions.

---

## ✨ Key Features

### 👨‍🏫 Teacher Features

* Create a course offering
* Add multiple sessions to an offering
* View teacher-specific offerings and sessions
* Create sessions in teacher's local timezone
* Store sessions internally in UTC

### 👨‍👩‍👧 Parent Features

* View all available offerings
* View session timings in parent’s local timezone
* Book an entire offering
* View booked offerings
* Prevent duplicate bookings
* Prevent overlapping bookings
* Handle simultaneous booking requests safely

---

## 🛠 Tech Stack Used

| Category          | Technology                 |
| ----------------- | -------------------------- |
| Language          | Java 21                    |
| Framework         | Spring Boot 3.2.5          |
| Web Layer         | Spring Web                 |
| ORM / Persistence | Spring Data JPA, Hibernate |
| Database          | MySQL 8                    |
| Build Tool        | Maven                      |
| Persistence API   | Jakarta Persistence API    |

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

## ⚙️ Setup Instructions

### 1. Prerequisites

Make sure the following are installed:

* Java 21
* Maven
* MySQL 8
* Git
* Postman or any API testing tool

---

### 2. Clone the Repository

```bash
git clone https://github.com/vedantsurkar/Undschool_Assessment
cd Undoschool_Assessment
```

---

### 3. Create MySQL Database

Login to MySQL and create the database:

```sql
CREATE DATABASE global_school;
```

---

### 4. Configure Application Properties

Open the following file:

```text
src/main/resources/application.properties
```

Add or update the configuration:

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

> ⚠️ Do not commit your real database password to GitHub.

---

## 🔐 Environment Variables Required

For local development, the project currently uses `application.properties`.

Required configuration values:

| Variable / Property                              | Description                    | Example                                                                     |
| ------------------------------------------------ | ------------------------------ | --------------------------------------------------------------------------- |
| `spring.datasource.url`                          | MySQL database connection URL  | `jdbc:mysql://localhost:3306/global_school?useSSL=false&serverTimezone=UTC` |
| `spring.datasource.username`                     | MySQL username                 | `root`                                                                      |
| `spring.datasource.password`                     | MySQL password                 | `your_mysql_password`                                                       |
| `spring.datasource.driver-class-name`            | MySQL JDBC driver              | `com.mysql.cj.jdbc.Driver`                                                  |
| `spring.jpa.hibernate.ddl-auto`                  | Hibernate schema update mode   | `update`                                                                    |
| `spring.jpa.properties.hibernate.jdbc.time_zone` | Forces Hibernate JDBC timezone | `UTC`                                                                       |

Optional future improvement:

```properties
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.url=${DB_URL}
```

---

## ▶️ Steps to Run the Application Locally

### Step 1: Start MySQL Server

Make sure MySQL is running on your machine.

---

### Step 2: Create Database

```sql
CREATE DATABASE global_school;
```

---

### Step 3: Run Spring Boot Application

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

### Step 4: Load Sample Data

After the application starts once and Hibernate creates the tables, run:

```bash
mysql -u root -p global_school < sample-data.sql
```

Or run `sample-data.sql` manually using MySQL Workbench.

---

## 🧪 Sample Data

The repository includes a `sample-data.sql` file containing sample data for:

* Teachers
* Courses
* Parents
* Offerings
* Sessions
* Bookings

Sample parents include different timezones:

```text
Alice Johnson - Asia/Kolkata
Bob Williams - Europe/London
Carol Martinez - America/Los_Angeles
```

---

## 🚀 API Documentation

Base URL:

```text
http://localhost:8080
```

---

# 👨‍🏫 Teacher APIs

## 1. Create Offering

Creates a new offering for a course and adds multiple sessions.

```http
POST /api/teachers/{teacherId}/offerings
```

### Request Body

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

### Success Response

```json
{
  "id": 1,
  "name": "Saturday Batch",
  "message": "Offering created with sessions"
}
```

---

## 2. Add Sessions to Offering

Adds more sessions to an existing offering.

```http
POST /api/teachers/offerings/{offeringId}/sessions
```

### Request Body

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

### Success Response

```text
Sessions added successfully
```

---

## 3. Get Teacher Offerings

Fetches all offerings created by a specific teacher.

```http
GET /api/teachers/{teacherId}/offerings
```

### Example Response

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

# 👨‍👩‍👧 Parent APIs

## 1. Get Available Offerings

Fetches all available offerings and displays session timings in the parent’s local timezone.

```http
GET /api/parents/{parentId}/offerings
```

### Example Response

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

## 2. Book Offering

Books an entire offering for a parent.

```http
POST /api/parents/{parentId}/bookings
```

### Request Body

```json
{
  "offeringId": 1
}
```

### Success Response

```text
Booking successful
```

### Conflict Response

```text
Cannot book: session conflicts with existing booking
```

---

## 3. Get Parent Bookings

Fetches all offerings booked by a parent.

```http
GET /api/parents/{parentId}/bookings
```

### Example Response

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

## 🗄 Database Schema Overview

The application uses the following tables:

| Table      | Purpose                                    |
| ---------- | ------------------------------------------ |
| `teacher`  | Stores teacher information                 |
| `course`   | Stores course/class details                |
| `offering` | Stores course offerings or sections        |
| `session`  | Stores session timings in UTC              |
| `parent`   | Stores parent/student details and timezone |
| `booking`  | Stores parent booking information          |

---

## 🔗 Entity Relationship Overview

```text
Teacher 1 ──── * Offering
Course  1 ──── * Offering
Offering 1 ─── * Session
Parent  1 ──── * Booking
Offering 1 ─── * Booking
```

---

## 📋 Main Entities

### Teacher

Stores teacher details.

```text
id
name
email
```

### Course

Stores course/class details.

```text
id
name
description
```

### Offering

Represents a schedulable section of a course.

```text
id
name
course_id
teacher_id
teacher_timezone
```

### Session

Represents an actual class timing.

```text
id
offering_id
start_time_utc
end_time_utc
```

### Parent

Stores parent/student details.

```text
id
name
email
timezone
version
```

The `version` field supports concurrency-related versioning.

### Booking

Stores offering-level bookings.

```text
id
parent_id
offering_id
booked_at
```

A unique constraint exists on:

```text
parent_id + offering_id
```

This prevents the same parent from booking the same offering more than once.

---

## 🔐 Concurrency Handling Approach

The system uses pessimistic locking during booking.

When a parent books an offering:

1. The parent record is fetched with a pessimistic write lock.
2. Existing bookings for that parent are loaded.
3. Sessions of the new offering are loaded.
4. Sessions of already booked offerings are compared with the new offering sessions.
5. If any overlap exists, the booking is rejected.
6. If there is no overlap, the booking is saved.

This approach ensures that two simultaneous requests from the same parent cannot create conflicting bookings.

### Why Pessimistic Locking?

Pessimistic locking is used because booking conflict detection requires checking existing records before inserting a new booking. Locking the parent row ensures only one booking operation for that parent is processed at a time.

This prevents race conditions such as:

```text
Request A checks conflicts -> no conflict found
Request B checks conflicts -> no conflict found
Both requests save conflicting bookings
```

With pessimistic locking, one request completes before the next request checks conflicts.

---

## 🔁 Booking Conflict Detection

A booking is rejected if any new session overlaps with any already booked session.

Conceptual overlap condition:

```text
newSession.start < existingSession.end
AND
newSession.end > existingSession.start
```

If overlap is found, the API returns:

```text
Cannot book: session conflicts with existing booking
```

---

## 🕒 Timezone Handling Approach

All session times are stored in UTC in the database.

### Teacher Flow

1. Teacher sends session start and end times in their local timezone.
2. The request also contains the teacher timezone.
3. The system converts teacher local time to UTC.
4. UTC time is stored in the `session` table.

Example:

```text
Teacher timezone: America/New_York
Teacher enters: 2025-06-07T18:00:00
Stored UTC: 2025-06-07T22:00:00
```

---

### Parent Flow

1. Parent has a timezone stored in the database.
2. Session time is fetched from the database in UTC.
3. UTC time is converted to the parent’s local timezone.
4. The converted time is returned in the API response.

Example:

```text
Parent timezone: Asia/Kolkata
Stored UTC: 2025-06-07T22:00:00
Displayed time: 2025-06-08 03:30
```

This ensures that teachers and parents can work in their own local timezone while the database remains consistent.

---

## 🧯 Error Handling

The application includes a global exception handler.

Common error responses:

```text
Teacher not found
Course not found
Offering not found
Parent not found
Cannot book: session conflicts with existing booking
```

Unexpected exceptions are handled using a generic internal server error response.

---

## 📝 Assumptions Made

* Authentication and authorization are not implemented because the assignment focuses on backend workflow and booking logic.
* Teachers, courses, and parents are expected to exist before creating offerings or bookings.
* Initial teacher, course, parent, offering, session, and booking data can be inserted through `sample-data.sql`.
* Parents book the complete offering, not individual sessions.
* All session times are stored internally in UTC.
* Timezones are expected to be valid IANA timezone values such as:

  * `Asia/Kolkata`
  * `Europe/London`
  * `America/New_York`
  * `America/Los_Angeles`
* Duplicate booking of the same offering by the same parent is not allowed.
* Conflict detection is done at the session level across all offerings booked by the parent.

---

## ✅ Assignment Requirement Coverage

| Requirement                    | Status        |
| ------------------------------ | ------------- |
| Project overview               | ✅ Included    |
| Tech stack used                | ✅ Included    |
| Setup instructions             | ✅ Included    |
| Environment variables required | ✅ Included    |
| API documentation              | ✅ Included    |
| Database schema overview       | ✅ Included    |
| Assumptions made               | ✅ Included    |
| Concurrency handling approach  | ✅ Included    |
| Timezone handling approach     | ✅ Included    |
| Steps to run locally           | ✅ Included    |
| Teacher can create offering    | ✅ Implemented |
| Teacher can add sessions       | ✅ Implemented |
| Teacher can view offerings     | ✅ Implemented |
| Parent can view offerings      | ✅ Implemented |
| Parent can book offering       | ✅ Implemented |
| Parent can view bookings       | ✅ Implemented |

---

## 🚧 Future Improvements

* Add Spring Security authentication and role-based authorization
* Add DTO classes instead of inner request classes
* Add service layer for better separation of business logic
* Add request validation using Bean Validation
* Add custom exception classes
* Add Swagger/OpenAPI documentation
* Add automated unit and integration tests
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
