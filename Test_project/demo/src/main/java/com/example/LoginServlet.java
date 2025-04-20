package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import org.json.JSONObject;

public class LoginServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();

        // Read incoming JSON data
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        JSONObject json = new JSONObject(sb.toString());
        String username = json.getString("username");
        String password = json.getString("password");

        try (Connection conn = DBUtil.getConnection()) {
            if (conn == null) {
                out.print("{\"success\": false, \"message\": \"Database connection failed.\"}");
                return;
            }

            // Check the username and password in the database
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // ✅ Create session and set username
                HttpSession session = request.getSession();
                session.setAttribute("username", username);

                out.print("{\"success\": true, \"message\": \"Login successful.\"}");
            } else {
                out.print("{\"success\": false, \"message\": \"Invalid username or password.\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"Database error occurred: " + e.getMessage() + "\"}");
        }
    }
}