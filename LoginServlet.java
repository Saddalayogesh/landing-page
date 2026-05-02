import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/WebAppDB", "root", "Yogesh@123");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                response.sendRedirect("welcome.html");
            } else {
                out.println("<html><body><h2>Login Failed</h2><p>Invalid username or password.</p><a href='login.html'>Try Again</a></body></html>");
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