package Dao.jdbc;

import Dao.StudentDao;
import model.Student;

import java.sql.*;
import java.util.*;

public class jdbcStudentDao implements StudentDao {


    private Connection connection;

    public jdbcStudentDao(Connection connection){
        this.connection = connection;
    }


    @Override
    public void addStudent(Student student){
        String sql = "INSERT INTO students (id, name, email) VALUES (? ,? ,?)";
        try( PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,Student.getId());
            stmt.setString(2, Student.getName());
            stmt.setString(3, Student.getEmail());
            stmt.executeUpdate();

        }catch(SQLException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public Student getStudentById(int id){
        String sql = "SELECT * FROM students where id= ? ";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            if(res.next()){
                return new Student(res.getInt("id"), res.getString("name"), res.getString("email"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();
         String sql = "SELECT * FROM students";
         try(Statement stmt = connection.createStatement()){
             ResultSet res = stmt.executeQuery(sql);
         }catch( SQLException e){
             e.printStackTrace();
         }
         return null;
    }

    @Override
    public void deleteStudent(int id){
        String sql = "DELETE FROM students where id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }



}
