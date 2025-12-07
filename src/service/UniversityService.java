package service;
import Dao.*;
import Dao.jdbc.*;
import model.*;
import util.DatabaseConnection;
import java.sql.*;
import java.util.List;




//this is the old service
public class UniversityService {
    private StudentDao studentDao;
    private CourseDao courseDao;
    private EnrollmentDao enrollmentDao;

    public UniversityService(){
        try{
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Connection = " + connection);
            this.studentDao = new JdbcStudentDao(connection);
            this.courseDao = new JdbcCourseDao(connection);
            this.enrollmentDao = new JdbcEnrollmentDao(connection);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //for student CRUD
    public void addStudent(Student student){
        studentDao.addStudent(student);
    }
    public Student getStudent(int id){
        return studentDao.getStudentById(id);
    }
    public List<Student> getStudents(){
       return  studentDao.getAllStudents();

    }
    public void removeStudent(int id){
        studentDao.deleteStudent(id);
    }

    //for courses CRUD
    public void addCourse(Course course){
        courseDao.addCourse(course);
    }
    public Course getCourse(int id){
        return courseDao.getCourseById(id);
    }
    public List<Course> getCourses(){
        return courseDao.getAllCourses();
    }
    public void removeCourse(int id){
        courseDao.deleteCourse(id);
    }

    //for enrollments CRUD
    public void addEnrollment(Enrollment enrollment){
        enrollmentDao.addEnrollment(enrollment);
    }
    public Enrollment getEnrollment(int id){
        return enrollmentDao.getEnrollment(id);
    }
    public List<Enrollment> getEnrollments(){
        return enrollmentDao.getAllEnrollments();
    }
    public void removeEnrollment(int id){
        enrollmentDao.deleteEnrollment(id);
    }

    public List<Course> getCoursesByStudent(int studentId){
        return enrollmentDao.getCoursesByStudent(studentId);
    }
    public List<Student> getStudentsByCourse(int courseId){
        return enrollmentDao.getStudentsByCourse(courseId);
    }







}
