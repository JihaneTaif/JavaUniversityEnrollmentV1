package ui;

import model.Student;
import service.StudentService;
import java.util.Scanner;

public class StudentUI {
    private StudentService studentService;
    private Scanner scanner;

    public StudentUI(StudentService studentService, Scanner scanner) {
        this.studentService = studentService;
        this.scanner = scanner;
    }

    public void addStudent() {
        System.out.print("Enter student Id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        studentService.addStudent(new Student(id, name, email));
        System.out.println("student created successfully::)");
    }

    public void listStudents() {
        studentService.getStudents().forEach(s ->
                System.out.println(s.getId() + " - " + s.getName() + " - " + s.getEmail()));
    }

    public void getStudent() {
        System.out.print("Enter student Id: ");
        int id = scanner.nextInt();
        Student s = studentService.getStudent(id);
        if (s != null) {
            System.out.println("Found: " + s.getName() + " (" + s.getEmail() + ")");
        } else {
            System.out.println("Student not found.");
        }
    }

    public void deleteStudent() {
        System.out.print("Enter student Id: ");
        int id = scanner.nextInt();
        studentService.removeStudent(id);
        System.out.println("student deleted successfully ::(");
    }
}
