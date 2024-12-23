<%@ page import="java.util.List" %>
<%@ page import="com.universityportal.Course" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Search Courses</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
    <!-- Search Form -->
    <form action="/university-portal" method="get">
        <label for="criteria">Search By:</label>
        <select name="criteria" id="criteria">
            <option value="id">ID</option>
            <option value="name">Name</option>
            <option value="department">Department</option>
        </select>
        <input type="text" name="value" required>
        <button type="submit">Search</button>
        <button type="button" onclick="window.location.href='/university-portal'">Clear</button>
    </form>

    <!-- Results Section -->
    <h2>Results:</h2>
    <% 
        List<Course> courses = (List<Course>) request.getAttribute("courses");
        if (courses != null && !courses.isEmpty()) { 
    %>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Department</th>
                <th>Credits</th>
            </tr>
            <% 
                for (Course course : courses) { 
            %>
                <tr>
                    <td><%= course.getId() %></td>
                    <td><%= course.getName() %></td>
                    <td><%= course.getDepartment() %></td>
                    <td><%= course.getCredits() %></td>
                    <td>
                        <a href="/university-portal/course/edit?id=<%= course.getId() %>">Edit</a>
                    </td>
                </tr>
            <% 
                } 
            %>
        </table>
    <% 
        } else { 
    %>
        <p>No courses found matching the search criteria.</p>
    <% 
        } 
    %>

    <!-- Button to Add New Course -->
    <button onclick="location.href='/university-portal/jsp/new_course.jsp'">New</button>
</body>
</html>
