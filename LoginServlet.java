import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// NOTE: The URL mapping in @WebServlet must match the form action in login.html
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    
    private static final String VALID_USER = "admin";
    private static final String VALID_PASS = "password123";

    // Handles POST requests from the HTML form
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // 2. Retrieve user credentials
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // 3. Validate credentials
        if (VALID_USER.equals(username) && VALID_PASS.equals(password)) {
            // Successful Login
            out.println("<!DOCTYPE html><html><head><title>Welcome</title></head><body>");
            out.println("<h2>&#x1F44B; Welcome, " + username + "! Your login was successful.</h2>");
            out.println("</body></html>");
        } else {
            // Failed Login
            out.println("<!DOCTYPE html><html><head><title>Error</title></head><body>");
            out.println("<h2>&#x274C; Login Failed!</h2>");
            out.println("<p>Invalid credentials. Please <a href='login.html'>try again</a>.</p>");
            out.println("</body></html>");
        }
    }
}
