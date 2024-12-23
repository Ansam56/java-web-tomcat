<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Error</title>
  </head>
  <body>
    <h1>Error occurred</h1>

    <p>
      <strong>Message:</strong> <%= request.getAttribute("error") != null ?
      request.getAttribute("error") : "An unexpected error occurred" %>
    </p>

    <p><a href="/university-portal">Go back to Home</a></p>
  </body>
</html>
