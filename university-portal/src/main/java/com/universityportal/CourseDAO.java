package com.universityportal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses ORDER BY ID";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getInt("credits")));
            }
        }
        return courses;
    }

    public List<Course> searchCourses(String criteria, String value) throws SQLException {
        List<Course> courses = new ArrayList<>();

        if (criteria == null
                || (!criteria.equals("id") && !criteria.equals("name") && !criteria.equals("department"))) {
            throw new IllegalArgumentException("Invalid search criteria: " + criteria);
        }

        System.out.println("Searching with criteria: " + criteria + " and value: " + value);

        String query = "SELECT * FROM courses WHERE " + criteria + " LIKE ? ORDER BY ID";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + value + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getInt("credits")));
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            throw e;
        }

        return courses;
    }

    public void addCourse(Course course) throws SQLException {
        String query = "INSERT INTO courses (name, department, credits) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getDepartment());
            stmt.setInt(3, course.getCredits());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Course successfully added to the database");
            } else {
                System.out.println("Failed to add the course");
            }
        }
    }

    public boolean updateCourse(Course course) throws SQLException {
        String query = "UPDATE courses SET name = ?, department = ?, credits = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, course.getName());
            stmt.setString(2, course.getDepartment());
            stmt.setInt(3, course.getCredits());
            stmt.setInt(4, course.getId());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0; // Returns true if the update was successful
        }
    }

    public Course getCourseById(int id) throws SQLException {
        Course course = null;
        String query = "SELECT * FROM courses WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String department = rs.getString("department");
                int credits = rs.getInt("credits");

                course = new Course(id, name, department, credits);
            }
        }
        return course;
    }
}
