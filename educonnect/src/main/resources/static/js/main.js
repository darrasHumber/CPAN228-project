/* ═══════════════════════════════════════════════
   EduConnect – Shared JS (sample data + helpers)
   ═══════════════════════════════════════════════ */

const COURSES = [
  { id:1,  code:"CS101",   title:"Introduction to Python Programming",    desc:"Learn Python from scratch. Variables, control flow, OOP, and file I/O. Perfect for beginners.",               instructor:"Dr. Sarah Chen",      category:"PROGRAMMING", status:"ACTIVE",    maxStudents:50, weeks:12 },
  { id:2,  code:"CS201",   title:"Web Development with Spring Boot",      desc:"Full-stack dev with Spring Boot, Thymeleaf, and Bootstrap. Includes REST APIs and DB integration.",          instructor:"Prof. James Miller",  category:"PROGRAMMING", status:"ACTIVE",    maxStudents:40, weeks:16 },
  { id:3,  code:"MATH200", title:"Calculus III – Multivariable Calculus", desc:"Partial derivatives, multiple integrals, vector calculus. Extends single-variable calculus.",              instructor:"Dr. Mohammed Darras", category:"MATHEMATICS", status:"ACTIVE",    maxStudents:35, weeks:14 },
  { id:4,  code:"MATH301", title:"Ordinary Differential Equations",       desc:"First and higher-order ODEs, Laplace transforms, and series solutions. Follows Boyce and DiPrima.",         instructor:"Dr. Mohammed Darras", category:"MATHEMATICS", status:"ACTIVE",    maxStudents:30, weeks:14 },
  { id:5,  code:"BUS101",  title:"Introduction to Business Management",   desc:"Foundations of strategy, operations, marketing, and finance. Case-study based approach.",                  instructor:"Prof. Linda Torres",  category:"BUSINESS",    status:"ACTIVE",    maxStudents:60, weeks:10 },
  { id:6,  code:"DATA301", title:"Data Science Fundamentals",             desc:"Python for data analysis: pandas, NumPy, matplotlib, and an introduction to machine learning.",             instructor:"Dr. Kevin Park",      category:"PROGRAMMING", status:"DRAFT",     maxStudents:45, weeks:12 },
  { id:7,  code:"LANG201", title:"Academic English Writing",              desc:"Essay structure, argumentation, citation formats, and research paper composition skills.",                   instructor:"Ms. Emma Walters",    category:"LANGUAGE",    status:"ACTIVE",    maxStudents:25, weeks:8  },
  { id:8,  code:"SCI201",  title:"Introduction to Physics",               desc:"Classical mechanics, thermodynamics, and wave phenomena. Calculus-based course with lab components.",       instructor:"Dr. Ali Hassan",      category:"SCIENCE",     status:"ACTIVE",    maxStudents:40, weeks:15 },
  { id:9,  code:"CS301",   title:"Algorithms and Data Structures",        desc:"Sorting, searching, trees, graphs, dynamic programming, and complexity theory.",                            instructor:"Prof. James Miller",  category:"PROGRAMMING", status:"COMPLETED", maxStudents:35, weeks:14 },
  { id:10, code:"MATH401", title:"Numerical Analysis",                    desc:"Root-finding, interpolation, numerical integration, and ODE solvers. Based on Burden and Faires.",          instructor:"Dr. Mohammed Darras", category:"MATHEMATICS", status:"DRAFT",     maxStudents:25, weeks:14 },
  { id:11, code:"ART101",  title:"Digital Design Fundamentals",           desc:"Visual design principles using Adobe tools. Color theory, typography, layout, and branding basics.",        instructor:"Ms. Chloe Martin",    category:"ARTS",        status:"ACTIVE",    maxStudents:30, weeks:10 },
  { id:12, code:"BUS301",  title:"Project Management Essentials",         desc:"Agile and Waterfall methodologies, Scrum ceremonies, risk management, and stakeholder communication.",      instructor:"Prof. Linda Torres",  category:"BUSINESS",    status:"ACTIVE",    maxStudents:50, weeks:8  },
];

const STRIPE_COLORS = {
  PROGRAMMING: '#1a56db', MATHEMATICS: '#7c3aed', BUSINESS: '#16a34a',
  SCIENCE: '#d97706',     LANGUAGE: '#be185d',    ARTS: '#c2410c', OTHER: '#64748b',
};

function cap(str) {
  if (!str) return '';
  return str.charAt(0) + str.slice(1).toLowerCase();
}

/* Active nav link */
document.addEventListener('DOMContentLoaded', () => {
  const page = window.location.pathname.split('/').pop() || 'index.html';
  document.querySelectorAll('.ec-navbar .nav-link').forEach(link => {
    if (link.getAttribute('href') === page) link.classList.add('active');
  });
});
