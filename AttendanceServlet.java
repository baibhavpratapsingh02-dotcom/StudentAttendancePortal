import java.io.IOException;
import java.sql.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
    
    // JDBC Connection Details (REPLACE WITH YOUR CONFIG)
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "your_password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Retrieve parameters
        String studentId = request.getParameter("studentId");
        String attDate = request.getParameter("attDate");
        String status = request.getParameter("status"); 

        Connection conn = null;
        PreparedStatement ps = null;
        String message;

        try {
            // 2. Load Driver and Establish Connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            
            // 3. Prepare and Execute INSERT Statement
            String sql = "INSERT INTO Attendance (student_id, att_date, status) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);
            ps.setDate(2, Date.valueOf(attDate)); // Convert String to java.sql.Date
            ps.setString(3, status);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                message = "Attendance saved successfully for Student ID: " + studentId;
            } else {
                message = "Failed to save attendance.";
            }

        } catch (Exception e) {
            message = "Database Error: " + e.getMessage();
        } finally {
            // 4. Close Resources
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* log error */ }
            try { if (conn != null) conn.close(); } catch (SQLException e) { /* log error */ }
        }

        // 5. Forward back to the JSP page to display the message
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/attendance_form.jsp");
        dispatcher.forward(request, response);
    }
}
