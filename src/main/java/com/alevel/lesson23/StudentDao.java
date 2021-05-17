package com.alevel.lesson23;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDao {
    static String URL = "jdbc:mysql://localhost:3306/university_db";
    static String USER = "root";
    static String PASSWORD = "ZaharValko031193";
    static String SELECT_BY_ID = "SELECT * From university_db.Student WHERE student_id = ?";
    static String SELECT_ALL = "SELECT * From university_db.Student";
    static String INSERT_STUDENT = "INSERT INTO university_db.Student (first_name, last_name, date_of_birth) VALUES (?, ?, ?)";
    static String UPDATE_STUDENT = "UPDATE university_db.Student SET student_id = ?, first_name = ?, last_name = ?, date_of_birth = ? WHERE student_id = ?";
    static String DELETE_STUDENT = "DELETE From university_db.Student WHERE student_id = ?";

    // Метод для добавления нового студента в список
    public Student addStudent(String first_name, String last_name, Date date_of_birth){
        Student studentToAdd = new Student(first_name, last_name, date_of_birth);
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(INSERT_STUDENT);
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setDate(3, new java.sql.Date(date_of_birth.getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentToAdd;
    }

    // Метод для обновления информации о студенте
    public Student updateStudent(long student_id, Student student){
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENT);
            statement.setLong(5, student_id);
            statement.setLong(1, student_id);
            statement.setString(2, student.getFirst_name());
            statement.setString(3, student.getLast_name());
            statement.setDate(4, new java.sql.Date(student.getDate_of_birth().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    // Метод для удаления студента из списка
    public boolean deleteStudent(long student_id){
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT);
            statement.setLong(1, student_id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Метод для вывода студента по id
    public Student getStudentById(long student_id) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, student_id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                return convertToModel(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // Метод для вывода всех студентов из списка
    public List getAll() {
        List listOfStudents = new ArrayList();
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                listOfStudents.add(convertToModel(result));
            }
            return listOfStudents;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Student convertToModel(ResultSet result) throws SQLException {
        long student_id = result.getLong("student_id");
        String first_name = result.getString("first_name");
        String last_name = result.getString("last_name");
        java.sql.Date date_of_birth = result.getDate("date_of_birth");
        return new Student(student_id,first_name,last_name,date_of_birth);
    }
}

