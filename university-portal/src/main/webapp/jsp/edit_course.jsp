<%@ page import="com.universityportal.Course" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Edit Course</title>
    <link rel="stylesheet" href="../css/edit.css">

</head>
<body>
    <h2>Edit Course</h2>
    <% 
        Course course = (Course) request.getAttribute("course"); 
        if (course != null) { 
    %>
        <form action="/university-portal/course/update" method="post">
            <input type="hidden" name="id" value="<%= course.getId() %>">
            <label for="name">Name:</label>
            <input type="text" name="name" value="<%= course.getName() %>" required><br>
            <label for="department">Department:</label>
            <input type="text" name="department" value="<%= course.getDepartment() %>" required><br>
            <label for="credits">Credits:</label>
            <input type="number" name="credits" value="<%= course.getCredits() %>" required><br>
            <button type="submit">Update</button>
        </form>
    <% 
        } else { 
    %>
        <p>Course not found.</p>
    <% 
        } 
    %>
</body>
</html>
