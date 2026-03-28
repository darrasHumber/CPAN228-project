-- ─────────────────────────────────────────────
--  EduConnect Seed Data
-- ─────────────────────────────────────────────

-- ── Admin ─────────────────────────────────────
-- Password: admin123
INSERT INTO users (first_name, last_name, username, email, password, role, created_at) VALUES
('Admin', 'User', 'admin', 'admin@educonnect.com',
 '$2a$10$oc2jp8JUVgVN/Z4jfLl0Ke9iOrovq4eZi/PhwrnHkx3PEdwBmq/E6',
 'ADMIN', CURRENT_TIMESTAMP);

-- ── Instructors ───────────────────────────────
-- All passwords: teacher123
INSERT INTO users (first_name, last_name, username, email, password, role, created_at) VALUES
('Mohammed', 'Darras',  'mdarras',  'mdarras@educonnect.com',
 '$2a$10$rHaOHfw44g3gHVIUkRm8SukBIG52GXqOlAwZLumztIihBMGO1V65O',
 'INSTRUCTOR', CURRENT_TIMESTAMP);

INSERT INTO users (first_name, last_name, username, email, password, role, created_at) VALUES
('Sarah',    'Chen',    'schen',    'schen@educonnect.com',
 '$2a$10$CaBt635hkYU.rA95RGlbuOQVGtaUsU80iwZ/h3AGTKFOBSbzoZ/Ti',
 'INSTRUCTOR', CURRENT_TIMESTAMP);

INSERT INTO users (first_name, last_name, username, email, password, role, created_at) VALUES
('James',    'Miller',  'jmiller',  'jmiller@educonnect.com',
 '$2a$10$SHC/wM0DfnkWaM40Ve05JerBNlUMwNJ76aGK2AaAArHOFrXOeZ1rG',
 'INSTRUCTOR', CURRENT_TIMESTAMP);

-- ── Students ──────────────────────────────────
-- Password: student123
INSERT INTO users (first_name, last_name, username, email, password, role, created_at) VALUES
('Ahmed', 'Rahim', 'arahim', 'arahim@educonnect.com',
 '$2a$10$EyF.dC8Z1Vf20Sh9r697ZOM3XVYqt81mTaRlnXeTxazFHVBOLSkCC',
 'STUDENT', CURRENT_TIMESTAMP);

-- ── Courses ───────────────────────────────────
-- Mohammed Darras → Math courses
INSERT INTO courses (course_code, title, description, instructor_name, category, status, max_students, duration_weeks, created_at) VALUES
('MATH200', 'Calculus III – Multivariable Calculus',
 'Partial derivatives, multiple integrals, and vector calculus.',
 'Dr. Mohammed Darras', 'MATHEMATICS', 'ACTIVE', 35, 14, CURRENT_TIMESTAMP),

('MATH301', 'Ordinary Differential Equations',
 'First and higher-order ODEs, Laplace transforms, and series solutions.',
 'Dr. Mohammed Darras', 'MATHEMATICS', 'ACTIVE', 30, 14, CURRENT_TIMESTAMP),

('MATH401', 'Numerical Analysis',
 'Root-finding, interpolation, numerical integration, and ODEs.',
 'Dr. Mohammed Darras', 'MATHEMATICS', 'DRAFT', 25, 14, CURRENT_TIMESTAMP),

('SCI201',  'Introduction to Physics',
 'Classical mechanics, thermodynamics, and wave phenomena.',
 'Dr. Mohammed Darras', 'SCIENCE', 'ACTIVE', 40, 15, CURRENT_TIMESTAMP);

-- Sarah Chen → Programming courses
INSERT INTO courses (course_code, title, description, instructor_name, category, status, max_students, duration_weeks, created_at) VALUES
('CS101',   'Introduction to Python Programming',
 'Learn Python from scratch. Covers variables, control flow, functions, OOP, and file I/O.',
 'Dr. Sarah Chen', 'PROGRAMMING', 'ACTIVE', 50, 12, CURRENT_TIMESTAMP),

('DATA301', 'Data Science Fundamentals',
 'Data analysis using Python. Covers pandas, NumPy, and machine learning basics.',
 'Dr. Sarah Chen', 'PROGRAMMING', 'DRAFT', 45, 12, CURRENT_TIMESTAMP),

('LANG201', 'Academic English Writing',
 'Essay structure, argumentation, citation, and research paper composition.',
 'Dr. Sarah Chen', 'LANGUAGE', 'ACTIVE', 25, 8, CURRENT_TIMESTAMP),

('ART101',  'Digital Design Fundamentals',
 'Color theory, typography, layout, and branding basics.',
 'Dr. Sarah Chen', 'ARTS', 'ACTIVE', 30, 10, CURRENT_TIMESTAMP);

-- James Miller → CS and Business courses
INSERT INTO courses (course_code, title, description, instructor_name, category, status, max_students, duration_weeks, created_at) VALUES
('CS201',   'Web Development with Spring Boot',
 'Full-stack web development using Spring Boot, Thymeleaf, and Bootstrap.',
 'Prof. James Miller', 'PROGRAMMING', 'ACTIVE', 40, 16, CURRENT_TIMESTAMP),

('CS301',   'Algorithms and Data Structures',
 'Sorting, searching, trees, graphs, dynamic programming, and complexity theory.',
 'Prof. James Miller', 'PROGRAMMING', 'COMPLETED', 35, 14, CURRENT_TIMESTAMP),

('BUS101',  'Introduction to Business Management',
 'Foundations of business: strategy, operations, marketing, and finance.',
 'Prof. James Miller', 'BUSINESS', 'ACTIVE', 60, 10, CURRENT_TIMESTAMP),

('BUS301',  'Project Management Essentials',
 'Agile, Scrum, risk management, and stakeholder communication.',
 'Prof. James Miller', 'BUSINESS', 'ACTIVE', 50, 8, CURRENT_TIMESTAMP);