package com.example.demo;

import java.io.IOException;
import java.io.Reader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletConfig;

import com.google.gson.Gson;

@WebServlet("/secured/cart/add")
@ServletSecurity(httpMethodConstraints = { @HttpMethodConstraint(value = "POST", rolesAllowed = { "role" }) })
public class SecuredPostCart extends HttpServlet {

    public void init(ServletConfig servletConfig) throws ServletException  {
        try {
            InitContext.setupInitialContext();
            Repository.init();
        } catch (NamingException e) {
            System.err.println(e.toString());
        } catch (SQLException e) {
            System.err.println(e.toString());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        try (PrintWriter writer = resp.getWriter()) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            Reader reader = req.getReader();
            Item item = new Gson().fromJson(reader, Item.class);
            try {
                Repository.addItem(item);
                String result = "Item added to cart : " + item.getProductName() + " Quantity : " + item.getQuantity() + " UserID: " + item.getUserId();
                resp.setStatus(200);
                resp.getWriter().print(result);
                resp.getWriter().close();
            } catch (SQLException e) {
                SqlExceptionMapper exceptionMapper = new SqlExceptionMapper();
                resp.setStatus(500);
                resp.getWriter().println(exceptionMapper.toResponse(new SqlException(new SqlExceptionInfo(e.getMessage(), e.getSQLState(), e.getErrorCode()))).getEntity());
                resp.getWriter().close();
            } catch (NamingException e) {
                resp.setStatus(500);
                resp.getWriter().println(e.getMessage());
                resp.getWriter().close();
            }
            catch (NullPointerException e){
                e.getStackTrace();
                resp.setStatus(500);
                resp.getWriter().print("Failed to establish DB connection. ");
                resp.getWriter().close();
            }
            
        }
        catch (UnsupportedEncodingException e) {
            resp.setStatus(500);
            resp.getWriter().print(e.getMessage());
            resp.getWriter().close();
        }
    }

}
