package Dao;


import model.Student;

import java.util.List;

public interface StudentDao {
    void addStudent(Student student);
    Student getStudentById(int id);
    List<Student> getAllStudents();
    void deleteStudent(int id);
}
