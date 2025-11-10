import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    
    // JDBC Connection Details (REPLACE WITH YOUR CONFIG)
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "your_password";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String employeeId = request.getParameter("id"); // Check for search parameter
        
        out.println("<!DOCTYPE html><html><head><title>Employee Records</title></head><body>");
        out.println("<h2>&#x1F464; Employee Directory</h2>");
        
        // --- Search Form ---
        out.println("<h3>Search by Employee ID</h3>");
        out.println("<form action='EmployeeServlet' method='get'>");
        out.println("ID: <input type='text' name='id' placeholder='Enter Employee ID'>");
        out.println("<input type='submit' value='Search'>");
        out.println("</form>");
        out.println("<hr>");
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 1. Load Driver and Establish Connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            // 2. Prepare SQL Query
            String sql;
            if (employeeId != null && !employeeId.trim().isEmpty()) {
                // Search query
                sql = "SELECT id, name, department FROM Employee WHERE id = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, employeeId);
                out.println("<h3>Search Results for ID: " + employeeId + "</h3>");
            } else {
                // Display all query
                sql = "SELECT id, name, department FROM Employee";
                ps = conn.prepareStatement(sql);
                out.println("<h3>All Employee List</h3>");
            }
            
            // 3. Execute Query
            rs = ps.executeQuery();

            // 4. Display Results
            out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Department</th></tr>");
            boolean found = false;
            while (rs.next()) {
                found = true;
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("department") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            if (!found) {
                out.println("<p>No employee records found.</p>");
            }

        } catch (Exception e) {
            out.println("<p style='color:red;'>Database Error: " + e.getMessage() + "</p>");
        } finally {
            // 5. Close Resources
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* log error */ }
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* log error */ }
            try { if (conn != null) conn.close(); } catch (SQLException e) { /* log error */ }
        }

        out.println("</body></html>");
    }
}
