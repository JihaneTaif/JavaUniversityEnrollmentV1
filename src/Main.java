import service.StudentService;
import service.CourseService;
import service.EnrollmentService;
import ui.StudentUI;
import ui.CourseUI;
import ui.EnrollmentUI;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Scanner scanner = new Scanner(System.in);

            // Create services
            StudentService studentService = new StudentService(connection);
            CourseService courseService = new CourseService(connection);
            EnrollmentService enrollmentService = new EnrollmentService(connection);

            // Create UI helpers
            StudentUI studentUI = new StudentUI(studentService, scanner);
            CourseUI courseUI = new CourseUI(courseService, scanner);
            EnrollmentUI enrollmentUI = new EnrollmentUI(enrollmentService, scanner);

            while (true) {
                printMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1: studentUI.addStudent(); break;
                    case 2: studentUI.getStudent(); break;
                    case 3: studentUI.listStudents(); break;
                    case 4: studentUI.deleteStudent(); break;
                    case 5: courseUI.addCourse(); break;
                    case 6: courseUI.getCourse(); break;
                    case 7: courseUI.listCourses(); break;
                    case 8: courseUI.deleteCourse(); break;
                    case 9: enrollmentUI.addEnrollment(); break;
                    case 10: enrollmentUI.getEnrollment(); break;
                    case 11: enrollmentUI.listEnrollments(); break;
                    case 12: enrollmentUI.deleteEnrollment(); break;
                    case 13: enrollmentUI.listCoursesByStudent(); break;
                    case 14: enrollmentUI.listStudentsByCourse(); break;
                    case 0: System.out.println("Goodbye!"); return;
                    default:  System.out.println("invalid choice.try again");
                }
                pause(scanner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printMenu() {
        System.out.println("1. Add Student");
        System.out.println("2. Get Student");
        System.out.println("3. List Students");
        System.out.println("4. Delete Student");
        System.out.println("5. Add Course");
        System.out.println("6. Get Course");
        System.out.println("7. List Courses");
        System.out.println("8. Delete Course");
        System.out.println("9. Add Enrollment");
        System.out.println("10. Get Enrollment");
        System.out.println("11. List Enrollments");
        System.out.println("12. Delete Enrollment");
        System.out.println("13. List Courses by Student");
        System.out.println("14. List Students by Course");
        System.out.println("0. Exit");
    }


    private static void pause(Scanner scanner){
        System.out.println("\n press enter to continue...");
        scanner.nextLine();
    }
}
