package service;

import Dao.CourseDao;
import Dao.jdbc.JdbcCourseDao;
import model.Course;

import java.sql.Connection;
import java.util.List;

public class CourseService {

    private CourseDao courseDao;

    public CourseService(Connection connection){
        this.courseDao = new JdbcCourseDao(connection);
    }

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
}
