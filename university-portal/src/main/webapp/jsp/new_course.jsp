<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create New Course</title>
    <link rel="stylesheet" href="../css/new.css">
</head>
<body>
    <h2>Create New Course</h2>
    <form action="/university-portal/course/create" method="POST">
        <label for="name">Course Name:</label>
        <input type="text" name="name" required /><br/>

        <label for="department">Department:</label>
        <input type="text" name="department" required /><br/>

        <label for="credits">Credits:</label>
        <input type="number" name="credits" required /><br/>

        <button type="submit">Create Course</button>
    </form>
</body>
</html>
