import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import org.json.JSONObject;

public class LoginServlet extends HttpServlet {
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
        String password = json.getString("password");

        JSONObject result = new JSONObject();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password); // hash comparison in real apps

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result.put("success", true);
            } else {
                result.put("success", false);
                result.put("message", "Invalid username or password.");
            }
        } catch (SQLException e) {
            result.put("success", false);
            result.put("message", "Database error.");
        }
        response.getWriter().print(result.toString());
    }
}
