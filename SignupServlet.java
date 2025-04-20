import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import org.json.JSONObject;

public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        JSONObject json = new JSONObject(sb.toString());
        String username = json.getString("username");
        String email = json.getString("email");
        String password = json.getString("password");

        JSONObject result = new JSONObject();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password); // hash this in production

            ps.executeUpdate();
            result.put("success", true);
        } catch (SQLException e) {
            result.put("success", false);
            result.put("message", "Username or email already exists.");
        }
        response.getWriter().print(result.toString());
    }
}
