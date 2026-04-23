# EduConnect – Online Learning Platform

**CPAN-228 Web Application Development | Winter 2026**  
**Group 20 – AMAL Coders**


## What is EduConnect?

EduConnect is a full-stack online learning platform where students can browse and enroll in courses, instructors can manage their own courses and view student stats, and administrators can manage the entire platform. Built with Spring Boot, Thymeleaf, Spring Security, and MySQL.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 4.0.3, Java 21 |
| Security | Spring Security 7, BCrypt |
| Database | H2 (dev), MySQL 8.0 (QA/Docker) |
| Frontend | Thymeleaf, Bootstrap 5.3 |
| Build | Maven |
| Deployment | Docker, Docker Compose |

---

## How to Run

### Option 1 — Local Development (H2)

```bash
git clone <your-repo-url>
cd educonnect
./mvnw spring-boot:run
```

Open `http://localhost:8080`

H2 Console → `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:educonnect`
- Username: `darras`
- Password: `root`

---

### Option 2 — Docker (MySQL) ✅ Recommended

**Requirements:** Docker Desktop installed and running

```bash
git clone <your-repo-url>
cd educonnect
docker compose up --build
```

Open `http://localhost:8080`

That's it — Docker starts MySQL and the app automatically.

**To stop:**
```bash
docker compose down
```

**To stop and remove all data:**
```bash
docker compose down -v
```

---

## Seed Accounts

| Username | Password | Role |
|---|---|---|
| `admin` | `admin123` | Admin |
| `mdarras` | `teacher123` | Instructor |
| `schen` | `teacher123` | Instructor |
| `jmiller` | `teacher123` | Instructor |
| `arahim` | `student123` | Student |

---

## Environment Profiles

| Profile | Database | How to activate |
|---|---|---|
| `dev` (default) | H2 in-memory | `./mvnw spring-boot:run` |
| `qa` | MySQL via Docker | `docker compose up --build` |

No code changes needed to switch environments — Spring Boot handles it automatically via `application.properties` and `application-qa.properties`.

---

## Features

### Deliverable 1 – Web Front-End & Database
- Home, About, How It Works pages
- Course list with search, filter by category and status, sort, pagination
- Add course form with server-side validation
- Course detail page
- 12 seed courses on startup
- Bootstrap 5 responsive layout

### Deliverable 2 – Security & User Management
- Custom login page with error messages
- BCrypt password encoding — passwords never stored in plain text
- Registration with role selection (Student or Instructor)
- Role-based redirect after login
- Protected routes — public pages open, sensitive routes require login
- Admin dashboard — view, edit, delete all courses
- Instructor dashboard — my courses with enrolled count and capacity progress bar
- Student dashboard — my enrolled courses with status
- Enrollment system — enroll and drop active courses
- Role-aware navbar — My Courses link for Student/Instructor, Admin link for Admin

### Deliverable 3 – Docker & Deployment
- Multi-stage Dockerfile (Maven build → JRE runtime)
- Docker Compose with MySQL 8.0 and health checks
- Spring profiles for dev (H2) and QA (MySQL)
- MySQL data persists between restarts via Docker volume
- `INSERT IGNORE` in `data.sql` for safe re-runs on MySQL

---

## Project Structure

```
src/main/
├── java/com/amalcoders/educonnect/
│   ├── config/SecurityConfig.java
│   ├── controller/
│   │   ├── HomeController.java
│   │   ├── AuthController.java
│   │   ├── CourseController.java
│   │   ├── StudentController.java
│   │   ├── InstructorController.java
│   │   └── AdminController.java
│   ├── model/
│   │   ├── Course.java
│   │   ├── User.java
│   │   ├── Enrollment.java
│   │   └── Role.java
│   ├── repository/
│   │   ├── CourseRepository.java
│   │   ├── UserRepository.java
│   │   └── EnrollmentRepository.java
│   └── service/
│       ├── CourseService.java
│       ├── UserService.java
│       └── EnrollmentService.java
└── resources/
    ├── templates/
    │   ├── fragments/      ← head, navbar, footer
    │   ├── auth/           ← login, register
    │   ├── courses/        ← list, form, detail
    │   ├── student/        ← dashboard
    │   ├── instructor/     ← dashboard, course-students
    │   └── admin/          ← dashboard, edit-course
    ├── data.sql
    ├── application.properties
    └── application-qa.properties
```

---

## Branch History

| Branch | Feature |
|---|---|
| `feature/security-setup` | Spring Security + BCrypt config |
| `feature/user-entity` | User entity, Role enum, UserRepository |
| `feature/registration` | Registration with BCrypt encoding |
| `feature/login` | Custom login page + Security filter chain |
| `feature/protected-routes` | Role-based route restrictions |
| `feature/admin` | Admin dashboard with edit and delete |
| `feature/registration-role` | Role dropdown + role-based redirect after login |
| `feature/enrollment` | Enrollment entity, enroll/drop, course detail |
| `feature/student-dashboard` | Student dashboard with enrolled courses |
| `feature/instructor-dashboard` | Instructor dashboard with course stats |
| `feature/fix-delete-enrolled-course` | Fix delete course with enrolled students |
| `feature/navbar-dashboard-links` | Role-based My Courses link in navbar |
| `feature/update-readme` | README updated for Deliverable 2 |
| `feature/spring-profiles` | Dev (H2) and QA (MySQL) Spring profiles |
| `feature/docker` | Dockerfile and Docker Compose |
| `feature/final-readme` | Final README with Docker instructions |