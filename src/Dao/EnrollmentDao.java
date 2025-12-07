package Dao;


import model.Course;
import model.Enrollment;
import model.Student;

import java.util.List;

public interface EnrollmentDao {
    void addEnrollment(Enrollment enrollment);
    Enrollment getEnrollment(int id);
    List<Enrollment> getAllEnrollments();
    void deleteEnrollment(int id);

    //joined relationships
    List<Course> getCoursesByStudent(int studentId);  //list courses specefic to some student
    List<Student> getStudentsByCourse(int courseId); //list students specefic to some course
}
