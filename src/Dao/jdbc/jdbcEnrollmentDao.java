package Dao.jdbc;

import Dao.EnrollmentDao;
import model.Course;
import model.Enrollment;
import model.Student;

import java.sql.*;
import java.util.*;

public class jdbcEnrollmentDao implements EnrollmentDao {
    private Connection connection;

    private jdbcEnrollmentDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public void addEnrollment(Enrollment enrollment){
        String sql = "INSERT INTO enrollments (id,studentId,courseId) VALUES(?,?,?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,enrollment.getId());
            stmt.setInt(2,enrollment.getStudentId());
            stmt.setInt(3,enrollment.getCourse_id());
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Enrollment> getAllEnrollments(){
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollements";
        try(Statement stmt = connection.createStatement()){
           ResultSet res = stmt.executeQuery(sql);
           if(res.next()){
               enrollments.add(new Enrollment(res.getInt("id"), res.getInt("studentId"), res.getInt("courseId")));
           }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public void deleteEnrollment(int id){
        String sql = "DELETE FROM enrollements WHERE id=?";
        try(PreparedStatement stmt= connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();

        }
    }

    @Override
    public List<Course> getCoursesByStudent(int studentId){
            List<Course> coursesByStudent = new ArrayList<>();
            String sql = "SELECT c.id , c.title FROM courses c " + "JOIN enrollments e ON c.id = e.courseId"
                            + "WHERE e.studentId = ?" ;


            try(PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setInt(1, studentId);
                ResultSet res = stmt.executeQuery();
                if(res.next()){
                    coursesByStudent.add(new Course(res.getInt("id"), res.getString("title")));
                }

            }catch (SQLException e ){
                e.printStackTrace();
            }
            return null;
    }

    @Override
    public List<Student> getStudentsByCourse(int courseId){
        List<Student> studentsByCourse = new ArrayList<>();
        String sql = "SELECT s.id, s.name, s.email FROM students " +
                "JOIN enrollements e ON s.id= e.studentId" + "WHERE e.courseId = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, courseId);
            ResultSet res = stmt.executeQuery();
            if(res.next()){
                studentsByCourse.add(new Student(res.getInt("id"), res.getString("name"),res.getString("email")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
