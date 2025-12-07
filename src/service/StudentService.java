package service;

import Dao.StudentDao;
import Dao.jdbc.JdbcStudentDao;
import model.Student;

import java.sql.*;
import java.util.*;

public class StudentService {
    private StudentDao studentDao;

    public StudentService(Connection connection) {
        this.studentDao = new JdbcStudentDao(connection);
    }

    public void addStudent(Student student) {
        studentDao.addStudent(student);
    }
    public Student getStudent(int id) {
        return studentDao.getStudentById(id);
    }
    public List<Student> getStudents() {
        return studentDao.getAllStudents();
    }
    public void removeStudent(int id) {
        studentDao.deleteStudent(id);
    }
}
