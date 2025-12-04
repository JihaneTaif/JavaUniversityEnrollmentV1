package Dao;

import model.Course;
import java.util.List;

public interface CourseDao {
    void addCourse(Course course);
    Course getCourseById(int id);
    List<Course> getAllCourses();
    void deleteCourse(int id);

}
