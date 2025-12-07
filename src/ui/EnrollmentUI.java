package ui;

import model.Enrollment;
import model.Course;
import model.Student;
import service.EnrollmentService;

import java.util.List;
import java.util.Scanner;

public class EnrollmentUI {
    private EnrollmentService enrollmentService;
    private Scanner scanner;

    public EnrollmentUI(EnrollmentService enrollmentService, Scanner scanner) {
        this.enrollmentService = enrollmentService;
        this.scanner = scanner;
    }

    // 9. Add a new enrollment
    public void addEnrollment() {
        System.out.print("Enter enrollment Id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter student Id: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter course Id: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();

        enrollmentService.addEnrollment(new Enrollment(id, studentId, courseId));
        System.out.println("Enrollment added successfully.");
    }

    // 10. Get a specific enrollment
    public void getEnrollment() {
        System.out.print("Enter enrollment Id: ");
        int id = scanner.nextInt();
        Enrollment e = enrollmentService.getEnrollment(id);
        if (e != null) {
            System.out.println("Enrollment found: ID=" + e.getId() +
                    ", Student=" + e.getStudentId() +
                    ", Course=" + e.getCourseId());
        } else {
            System.out.println("Enrollment not found.");
        }
    }

    // 11. List all enrollments
    public void listEnrollments() {
        List<Enrollment> enrollments = enrollmentService.getEnrollments();
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
        } else {
            enrollments.forEach(e ->
                    System.out.println("Enrollment ID=" + e.getId() +
                            ", Student=" + e.getStudentId() +
                            ", Course=" + e.getCourseId()));
        }
    }

    // 12. Delete an enrollment
    public void deleteEnrollment() {
        System.out.print("Enter enrollment Id: ");
        int id = scanner.nextInt();
        enrollmentService.removeEnrollment(id);
        System.out.println("Enrollment deleted successfully.");
    }

    // 13. List all courses by student
    public void listCoursesByStudent() {
        System.out.print("Enter student Id: ");
        int studentId = scanner.nextInt();
        List<Course> courses = enrollmentService.getCoursesByStudent(studentId);
        if (courses.isEmpty()) {
            System.out.println("No courses found for this student.");
        } else {
            courses.forEach(c ->
                    System.out.println("Course ID=" + c.getId() + ", Title=" + c.getTitle()));
        }
    }

    // 14. List all students by course
    public void listStudentsByCourse() {
        System.out.print("Enter course Id: ");
        int courseId = scanner.nextInt();
        List<Student> students = enrollmentService.getStudentsByCourse(courseId);
        if (students.isEmpty()) {
            System.out.println("No students found for this course.");
        } else {
            students.forEach(s ->
                    System.out.println("Student ID=" + s.getId() +
                            ", Name=" + s.getName() +
                            ", Email=" + s.getEmail()));
        }
    }
}
