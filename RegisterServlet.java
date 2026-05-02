import java.io.IOException;
import java.sql.*;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/WebAppDB", "root", "Yogesh@123");

            PreparedStatement ps = con.prepareStatement("INSERT INTO users (email, password) VALUES (?, ?)");
            ps.setString(1, email);
            ps.setString(2, password);
            int result = ps.executeUpdate();

            if (result > 0) {
                out.println("<html><body><h2>Registration Successful!</h2><a href='login.html'>Login Here</a></body></html>");
            } else {
                out.println("<html><body><h2>Registration Failed.</h2><a href='register.html'>Try Again</a></body></html>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<html><body><h2>Error Occurred</h2><p>Something went wrong. Please try again later.</p></body></html>");
        } finally {
            out.close();
        }
    }
}