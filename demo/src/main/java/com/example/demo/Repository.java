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
        String sql = "CREATE TABLE IF NOT EXISTS cart ( \n" +
        "  id INT NOT NULL PRIMARY KEY , \n" +
        "  name VARCHAR(100) NOT NULL , \n" +
        "  quantity INT NOT NULL \n" +
        ");";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute( sql );
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }

    public static String getCart(){
        Statement stmt = null;
        ResultSet rs = null;
        String res = "";

        init();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT JSON_ARRAYAGG(JSON_OBJECT('id', id, 'name', name, 'quantity', quantity)) from cart;");

            rs = stmt.getResultSet();
            // ResultSetMetaData rsmd = rs.getMetaData();
            // int columnCount = rsmd.getColumnCount();

            // // The column count starts from 1
            // for (int i = 1; i <= columnCount; i++ ) {
            //     String name = rsmd.getColumnName(i);
            //     System.out.println("column "+Integer.toString(i)+" "+name);
            // }
            while(rs.next()){
                res = res + rs.getString("JSON_ARRAYAGG(JSON_OBJECT('id', id, 'name', name, 'quantity', quantity))");
            }

        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {

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
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlEx) { } // ignore

                conn = null;
            }
        }
        return res;
    }

    public static void deleteItemById(int id){
        Statement statement = null;
        init();

        try {
            String sql = "DELETE FROM cart " +
            "WHERE id = "+Integer.toString(id);
            statement = conn.createStatement();
            statement.executeUpdate(sql);
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqlEx) { } // ignore

                statement = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlEx) { } // ignore

                conn = null;
            }
        }
    }

    public static void addItem(Item item){
        PreparedStatement preparedStatement = null;
        init();

        try {
            String statement = "INSERT INTO cart ( id , name , quantity ) VALUES ( ? , ? , ? ); ";
            preparedStatement = conn.prepareStatement(statement) ;
            preparedStatement.setInt( 1 , item.getId() );
            preparedStatement.setString( 2 , item.getName() );
            preparedStatement.setInt( 3 , item.getQuantity() );
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException sqlEx) { } // ignore

                preparedStatement = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlEx) { } // ignore

                conn = null;
            }
        }
    }

}
