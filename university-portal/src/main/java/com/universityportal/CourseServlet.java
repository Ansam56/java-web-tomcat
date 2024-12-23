package com.universityportal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CourseServlet extends HttpServlet {
    private CourseDAO courseDAO;

    @Override
    public void init() {
        courseDAO = new CourseDAO(); // Initialize the DAO
    }

    // Handle GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath(); // Get the servlet path
        String criteria = request.getParameter("criteria");
        String value = request.getParameter("value");
        System.out.println("path in GET = " + path);

        try {
            if ("/course/edit".equals(path)) {
                // Handle edit view request
                handleEditCourseView(request, response);
            } else {
                // Handle search and display view request
                handleCoursesView(request, response, criteria, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    // Handle POST requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println("Path in POST = " + path);

        // Handle course creation
        if ("/university-portal/course/create".equals(path)) {
            handleCreateCourse(request, response);
        }
        // Handle course update
        else if ("/university-portal/course/update".equals(path)) {

            handleUpdateCourse(request, response);
        } else {
            // Handle other post requests or errors
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown path: " + path);
        }
    }

    // Method to show the edit course view
    private void handleEditCourseView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("error", "Course ID is required for editing.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            int courseId = Integer.parseInt(idParam);
            System.out.println("Course ID = " + courseId);

            Course course = courseDAO.getCourseById(courseId); // Fetch course by ID

            if (course == null) {
                request.setAttribute("error", "Course not found.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
                dispatcher.forward(request, response);
                return;
            }

            request.setAttribute("course", course); // Add course to request
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/edit_course.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid course ID format.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while editing the course.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    // Method to show courses view
    private void handleCoursesView(HttpServletRequest request, HttpServletResponse response,
            String criteria, String value)
            throws ServletException, IOException {
        List<Course> courses = new ArrayList<>();

        try {
            if (criteria == null || value == null || criteria.isEmpty() || value.isEmpty()) {
                // Fetch all courses if no search criteria are provided
                courses = courseDAO.getAllCourses();
            } else {
                // Perform search based on criteria and value
                courses = courseDAO.searchCourses(criteria, value);
            }

            request.setAttribute("courses", courses); // Add courses to request
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/main.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while searching for courses.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    // Method for course creation
    private void handleCreateCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String department = request.getParameter("department");
        String creditsParam = request.getParameter("credits");

        if (name == null || name.isEmpty() || department == null || department.isEmpty() || creditsParam == null
                || creditsParam.isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            int credits = Integer.parseInt(creditsParam);
            Course newCourse = new Course(name, department, credits);

            courseDAO.addCourse(newCourse); // Add course to the database
            response.sendRedirect("/university-portal"); // Redirect to main page after course creation
        } catch (SQLException e) {
            request.setAttribute("error", "Error creating course: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid credits format.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    // Method for course update
    private void handleUpdateCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the course ID and updated information from the request
        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String department = request.getParameter("department");
        String creditsParam = request.getParameter("credits");

        if (idParam == null || idParam.isEmpty() || name == null || name.isEmpty() || department == null
                || department.isEmpty() || creditsParam == null || creditsParam.isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            int courseId = Integer.parseInt(idParam);
            int credits = Integer.parseInt(creditsParam);

            // Create an updated Course object and update it in the database
            Course updatedCourse = new Course(courseId, name, department, credits);
            boolean isUpdated = courseDAO.updateCourse(updatedCourse);

            if (isUpdated) {
                response.sendRedirect("/university-portal"); // Redirect to the main page
            } else {
                request.setAttribute("error", "Failed to update course.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
                dispatcher.forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid course ID or credits format.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while updating the course.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }

}
