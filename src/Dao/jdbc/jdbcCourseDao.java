package Dao.jdbc;

import Dao.CourseDao;
import model.Course;

import java.sql.*;
import java.util.*;

public class jdbcCourseDao implements CourseDao {

    private Connection connection;

    public jdbcCourseDao(Connection connection){
        this.connection =  connection;
    }

    @Override
    public void addCourse(Course course){
        String sql = "INSERT INTO courses (id,title) VALUES (?,?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql) ){
            stmt.setInt(1, course.getId());
            stmt.setString(2, course.getTitle());
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Course getCourseById( int id){
        String sql = "SELECT * FROM courses WHERE id= ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            ResultSet res = stmt.executeQuery();
            if(res.next()){
                return new Course(res.getInt("id"), res.getString("title"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Course> getAllCourses(){
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try(Statement stmt = connection.createStatement()){
            ResultSet res = stmt.executeQuery(sql);
            if(res.next()){
                courses.add(new Course(res.getInt("id"), res.getString("title")));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteCourse(int id){
        String sql = "DELETE FROM courses WHERE id= ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }



    }

}
