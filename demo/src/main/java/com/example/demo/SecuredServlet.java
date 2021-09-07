package com.example.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/secured")
@ServletSecurity(httpMethodConstraints = { @HttpMethodConstraint(value = "GET", rolesAllowed = { "role" }) })
public class SecuredServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.println("<html>");
            writer.println("  <head><title>CART SERVICE</title></head>");
            writer.println("  <body>");
            writer.print("<h1>Welcome ");
            Principal user = req.getUserPrincipal();
            writer.print(user != null ? user.getName() : "ANONYMOUS");
            writer.print(" to cart service </h1>");
            writer.println("");
            writer.println("<h2><a href=secured/cart>Access cart for all users. </a></h2>");
            writer.println("<h2><a href=secured/users>Access users. </a></h2>");
            writer.println("<h2><a href=secured/activityLog>Access the activity log. </a></h2>");
            writer.println("  </body>");
            writer.println("</html>");
            
        }
    }

}
