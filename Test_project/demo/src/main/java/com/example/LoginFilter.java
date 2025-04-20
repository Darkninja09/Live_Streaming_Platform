package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginFilter implements Filter {

    public void init(FilterConfig filterConfig) {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // Only allow if session has username set
        if (session != null && session.getAttribute("username") != null) {
            chain.doFilter(request, response); // ✅ Allow access
        } else {
            res.sendRedirect("index.html"); // 🔒 Block access, redirect to homepage
        }
    }

    public void destroy() {}
}