package com.example.demo;

import org.apache.commons.dbcp.BasicDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class InitContext {

    public static void setupInitialContext() throws NamingException{
        BasicDataSource ds = new BasicDataSource();
        // Define Driver Class
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
 
		// Define Server URL
		ds.setUrl("jdbc:mysql://localhost:3306/restdb");
 
		// Define Username
		ds.setUsername("root");
 
		// Define Your Password
		ds.setPassword("admin");

        Context ctx;
        ctx = new InitialContext();
        ctx.bind("jdbc/mysqldb", ds);
        
    }
    
}
