package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class SessionCheckServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("username") != null) {
            out.print("{\"loggedIn\": true, \"username\": \"" + session.getAttribute("username") + "\"}");
        } else {
            out.print("{\"loggedIn\": false}");
        }
    }
}
