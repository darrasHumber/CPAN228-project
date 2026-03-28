# EduConnect – Online Learning Platform

CPAN-228 Web Application Development | Group 20 – AMAL Coders

---

## Tech Stack
- Spring Boot 4.0.3, Spring Data JPA, Spring Security 7, Spring Validation
- Thymeleaf + Thymeleaf Security Extras, Bootstrap 5.3
- H2 in-memory database
- Maven, Java 21

---

## How to Run
```bash
git clone <your-repo-url>
cd educonnect
./mvnw spring-boot:run
```
Open `http://localhost:8080`

H2 Console → `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:educonnect`
- Username: `darras` | Password: `root`

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

## Project Structure
```
src/main/
├── java/com/amalcoders/educonnect/
│   ├── config/
│   │   └── SecurityConfig.java
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
    ├── static/css/style.css
    ├── templates/
    │   ├── fragments/
    │   │   ├── head.html
    │   │   ├── navbar.html
    │   │   └── footer.html
    │   ├── auth/
    │   │   ├── login.html
    │   │   └── register.html
    │   ├── courses/
    │   │   ├── list.html
    │   │   ├── form.html
    │   │   └── detail.html
    │   ├── student/
    │   │   └── dashboard.html
    │   ├── instructor/
    │   │   ├── dashboard.html
    │   │   └── course-students.html
    │   ├── admin/
    │   │   ├── dashboard.html
    │   │   └── edit-course.html
    │   ├── home.html
    │   ├── about.html
    │   └── how-it-works.html
    ├── data.sql
    └── application.properties
```

---

## Deliverable 1 – Web Front-End & Database ✅
- Home, About, How It Works pages
- Course list with search, filter by category + status, sort, pagination
- Add course form with server-side validation
- Course detail page with flash message
- 12 seed courses on startup
- Bootstrap 5 responsive layout
- Shared Thymeleaf fragments (head, navbar, footer)

## Deliverable 2 – Security & User Management ✅

### Authentication
- Custom login page with error messages
- BCrypt password encoding
- Register with role selection (Student or Instructor)
- Login redirects by role → Admin to `/admin`, Instructor to `/instructor`, Student to `/dashboard`
- Logout with confirmation message

### Roles & Access Control
| Role | Access |
|---|---|
| ADMIN | Everything + admin dashboard (edit/delete courses) |
| INSTRUCTOR | Add courses + instructor dashboard (my courses + stats) |
| STUDENT | Browse courses + enroll/drop + student dashboard |
| Public | Home, About, How It Works, Login, Register |

### Enrollment System
- Students can enroll in ACTIVE courses
- Students can drop courses
- Enrolled count shown on course detail
- Deleting a course automatically removes all enrollments

### Dashboards
- **Student** `/dashboard` — enrolled courses list with status and stats
- **Instructor** `/instructor` — my courses with enrolled count, capacity progress bar, student list per course
- **Admin** `/admin` — all courses with edit and delete

### Navbar
- Role-aware: shows My Courses for Student/Instructor, Admin link for Admin
- Shows logged-in username with dropdown
- Login/Register shown only when not authenticated

---

## Branch History
| Branch | Feature |
|---|---|
| `feature/security-setup` | Spring Security + BCrypt config |
| `feature/user-entity` | User entity, Role enum, UserRepository |
| `feature/registration` | Registration flow with BCrypt encoding |
| `feature/login` | Custom login page + Security filter chain |
| `feature/protected-routes` | Role-based route restrictions |
| `feature/admin` | Admin dashboard with edit/delete |
| `feature/registration-role` | Role dropdown on register + role-based redirect |
| `feature/enrollment` | Enrollment entity, enroll/drop, course detail update |
| `feature/student-dashboard` | Student dashboard with enrolled courses |
| `feature/instructor-dashboard` | Instructor dashboard with course stats |
| `feature/fix-delete-enrolled-course` | Fix delete course with enrolled students |
| `feature/navbar-dashboard-links` | Role-based My Courses link in navbar |

---

## Coming Next
- Deliverable 3 – Microservice, REST API, Spring Profiles, Docker