package ui;

import model.Course;
import service.CourseService;
import java.util.Scanner;

public class CourseUI {
    private CourseService courseService;
    private Scanner scanner;

    public CourseUI(CourseService courseService, Scanner scanner) {
        this.courseService = courseService;
        this.scanner = scanner;
    }

    public void addCourse() {
        System.out.print("Enter course Id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        courseService.addCourse(new Course(id, title));
        System.out.println("course created successfully ::)");
    }

    public void listCourses() {

        courseService.getCourses().forEach(c ->
                System.out.println(c.getId() + " - " + c.getTitle()) );
    }

    public void getCourse() {
        System.out.print("Enter course Id: ");
        int id = scanner.nextInt();
        Course c = courseService.getCourse(id);
        if (c != null) {
            System.out.println("Found: " + c.getId() + " (" + c.getTitle() + ")");
        } else {
            System.out.println("course not found.");
        }
    }

        public void deleteCourse() {
        System.out.print("Enter course Id: ");
        int id = scanner.nextInt();
        courseService.removeCourse(id);
            System.out.println("course deleted successfully ::(");
    }
}
