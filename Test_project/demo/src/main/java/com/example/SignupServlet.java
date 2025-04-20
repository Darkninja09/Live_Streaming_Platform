package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import org.json.JSONObject;

public class SignupServlet extends HttpServlet {
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
        String email = json.getString("email");
        String password = json.getString("password");

        try (Connection conn = DBUtil.getConnection()) {
            if (conn == null) {
                out.print("{\"success\": false, \"message\": \"Database connection failed.\"}");
                return;
            }

            // Check if the username already exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                out.print("{\"success\": false, \"message\": \"Username already exists.\"}");
                return;
            }

            // Insert new user into the database
            PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)");
            insertStmt.setString(1, username);
            insertStmt.setString(2, email);
            insertStmt.setString(3, password);

            int rowsAffected = insertStmt.executeUpdate();
            if (rowsAffected > 0) {
                out.print("{\"success\": true, \"message\": \"Account created successfully!\"}");
            } else {
                out.print("{\"success\": false, \"message\": \"Error creating account.\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"Database error occurred: " + e.getMessage() + "\"}");
        }
    }
}