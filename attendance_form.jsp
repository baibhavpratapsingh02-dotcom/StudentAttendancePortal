<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Attendance Portal</title>
</head>
<body>
    <h2>&#x1F4DD; Student Attendance Entry</h2>
    <form action="AttendanceServlet" method="post">
        
        <label for="studentId">Student ID:</label>
        <input type="text" id="studentId" name="studentId" required><br><br>
        
        <label for="attDate">Date:</label>
        <input type="date" id="attDate" name="attDate" required><br><br>
        
        <label>Attendance Status:</label><br>
        <input type="radio" id="present" name="status" value="Present" required>
        <label for="present">Present</label><br>
        
        <input type="radio" id="absent" name="status" value="Absent">
        <label for="absent">Absent</label><br><br>
        
        <input type="submit" value="Save Attendance">
    </form>
    
    <%-- Display success/error message if redirected --%>
    <% 
        String message = (String) request.getAttribute("message");
        if (message != null) {
            out.println("<p style='color: green;'>" + message + "</p>");
        } 
    %>
</body>
</html>
