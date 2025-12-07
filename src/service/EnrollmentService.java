package service;

import Dao.*;
import Dao.jdbc.*;
import model.*;

import java.sql.Connection;
import java.util.List;

public class EnrollmentService {
    private EnrollmentDao enrollmentDao;

    public EnrollmentService(Connection connection) {
        this.enrollmentDao = new JdbcEnrollmentDao(connection);
    }

    public void addEnrollment(Enrollment enrollment) { enrollmentDao.addEnrollment(enrollment); }
    public Enrollment getEnrollment(int id) { return enrollmentDao.getEnrollment(id); }
    public List<Enrollment> getEnrollments() { return enrollmentDao.getAllEnrollments(); }
    public void removeEnrollment(int id) { enrollmentDao.deleteEnrollment(id); }

    public List<Course> getCoursesByStudent(int studentId) {
        return enrollmentDao.getCoursesByStudent(studentId);
    }
    public List<Student> getStudentsByCourse(int courseId) {
        return enrollmentDao.getStudentsByCourse(courseId);
    }
}
