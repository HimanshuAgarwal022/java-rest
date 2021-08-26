package com.example.demo;

import java.sql.*;

public class Repository {

    private static Connection conn = null;

    private static void init(){
        try {
            conn =
               DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_data?" +
                                           "user=root&password=admin");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public static String sqlQuery(String query){
        Statement stmt = null;
        ResultSet rs = null;
        String res = "";

        init();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...
            rs = stmt.getResultSet();
            rs.next();
            res = rs.getString("Tables_in_spring_data");
            
            // if (stmt.execute("SELECT foo FROM bar")) {
                
            // }
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore

                stmt = null;
            }
        }
        return res;
    }

}
