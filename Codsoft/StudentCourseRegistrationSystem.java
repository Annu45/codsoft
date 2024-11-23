import java.util.*;

public class StudentCourseRegistrationSystem {

    static class Course {
        String courseCode;
        String title;
        String description;
        int capacity;
        int enrolled;

        Course(String courseCode, String title, String description, int capacity) {
            this.courseCode = courseCode;
            this.title = title;
            this.description = description;
            this.capacity = capacity;
            this.enrolled = 0;
        }

        boolean isAvailable() {
            return enrolled < capacity;
        }

        void enroll() {
            if (isAvailable()) {
                enrolled++;
            }
        }

        void drop() {
            if (enrolled > 0) {
                enrolled--;
            }
        }

        @Override
        public String toString() {
            return "Course Code: " + courseCode + "\n" +
                   "Title: " + title + "\n" +
                   "Description: " + description + "\n" +
                   "Capacity: " + capacity + "\n" +
                   "Enrolled: " + enrolled + "\n";
        }
    }

    static class Student {
        String studentId;
        String name;
        List<Course> registeredCourses;

        Student(String studentId, String name) {
            this.studentId = studentId;
            this.name = name;
            this.registeredCourses = new ArrayList<>();
        }

        void registerCourse(Course course) {
            if (course.isAvailable() && !registeredCourses.contains(course)) {
                registeredCourses.add(course);
                course.enroll();
                System.out.println("Successfully registered for " + course.title);
            } else {
                System.out.println("Course is full or already registered.");
            }
        }

        void dropCourse(Course course) {
            if (registeredCourses.contains(course)) {
                registeredCourses.remove(course);
                course.drop();
                System.out.println("Successfully dropped " + course.title);
            } else {
                System.out.println("You are not registered for this course.");
            }
        }

        void displayRegisteredCourses() {
            if (registeredCourses.isEmpty()) {
                System.out.println("You have not registered for any courses.");
            } else {
                System.out.println("Registered Courses:");
                for (Course course : registeredCourses) {
                    System.out.println(course.title);
                }
            }
        }
    }

    private static List<Course> courses = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadCourses();
        mainMenu();
    }

    private static void loadCourses() {
        courses.add(new Course("CS101", "Introduction to Computer Science", "Basic concepts of programming", 30));
        courses.add(new Course("MATH201", "Calculus I", "Fundamentals of calculus", 25));
        courses.add(new Course("PHY101", "Physics I", "Introduction to mechanics", 20));
        courses.add(new Course("ENG102", "English Literature", "Study of English novels and poetry", 40));
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\nStudent Course Registration System");
            System.out.println("1. Register as a new student");
            System.out.println("2. Login as an existing student");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerStudent();
                    break;
                case 2:
                    studentLogin();
                    break;
                case 3:
                    System.out.println("Exiting the system.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void registerStudent() {
        System.out.print("Enter your student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        students.add(new Student(studentId, name));
        System.out.println("Student registered successfully!");
    }

    private static void studentLogin() {
        System.out.print("Enter your student ID: ");
        String studentId = scanner.nextLine();

        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found. Please register first.");
            return;
        }

        studentMenu(student);
    }

    private static void studentMenu(Student student) {
        while (true) {
            System.out.println("\nWelcome, " + student.name);
            System.out.println("1. View available courses");
            System.out.println("2. Register for a course");
            System.out.println("3. Drop a course");
            System.out.println("4. View registered courses");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAvailableCourses();
                    break;
                case 2:
                    registerForCourse(student);
                    break;
                case 3:
                    dropCourse(student);
                    break;
                case 4:
                    student.displayRegisteredCourses();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void viewAvailableCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private static void registerForCourse(Student student) {
        System.out.print("Enter the course code to register: ");
        String courseCode = scanner.nextLine();
        Course course = findCourseByCode(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        student.registerCourse(course);
    }

    private static void dropCourse(Student student) {
        System.out.print("Enter the course code to drop: ");
        String courseCode = scanner.nextLine();
        Course course = findCourseByCode(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        student.dropCourse(course);
    }

    private static Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.courseCode.equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    private static Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.studentId.equals(studentId)) {
                return student;
            }
        }
        return null;
    }
}

