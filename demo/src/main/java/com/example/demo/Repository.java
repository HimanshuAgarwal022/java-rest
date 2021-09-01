package com.example.demo;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.google.gson.Gson;

import org.json.JSONArray;

public class Repository {
    
    private static Connection conn = null;

    protected static void init() throws SQLException, NamingException {
        Context ctx;
        DataSource ds;
        ctx = new InitialContext();
        ds = (DataSource) ctx.lookup("jdbc/mysqldb");
        conn = ds.getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS cart ( \n" +
        "  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY , \n" +
        "  productId INT NOT NULL , \n" +
        "  productName VARCHAR(100) NOT NULL , \n" +
        "  quantity INT NOT NULL , \n" +
        "  userId INT NOT NULL \n" +
        ");";

        Statement stmt = null;

        stmt = conn.createStatement();
        stmt.execute( sql );

        String sql2 = "CREATE TABLE IF NOT EXISTS users ( \n" +
        "  userId INT NOT NULL AUTO_INCREMENT PRIMARY KEY , \n" +
        "  userName VARCHAR(100) NOT NULL \n" +
        ");";

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) { } // ignore

            stmt = null;
        }

        stmt = conn.createStatement();
        stmt.execute(sql2);

        String sql3 = "CREATE TABLE IF NOT EXISTS activityLog ( \n" +
        "  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY , \n" +
        "  userId INT NOT NULL , \n" +
        "  productId INT NOT NULL , \n" +
        "  productName VARCHAR(100) NOT NULL , \n" +
        "  quantity INT NOT NULL, \n" +
        "  loggedAt DATETIME DEFAULT CURRENT_TIMESTAMP \n" +
        ");";

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) { } // ignore

            stmt = null;
        }

        stmt = conn.createStatement();
        stmt.execute(sql3);

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) { } // ignore

            stmt = null;
        }

    }

    public static String getCart() throws SQLException, NamingException {
        Statement stmt = null;
        ResultSet rs = null;
        String res = "";

        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM cart;");
        rs = stmt.getResultSet();
        JSONArray response = ResultSetJsonMapper.convert(rs);
        res = response.toString();

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
        return res;
    }

    public static String getActivityLog() throws SQLException, NamingException {
        Statement stmt = null;
        ResultSet rs = null;
        String res = "";

        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM activityLog;");
        rs = stmt.getResultSet();
        JSONArray response = ResultSetJsonMapper.convert(rs);
        res = response.toString();

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
        return res;
    }

    public static String getUsers() throws SQLException, NamingException {
        Statement stmt = null;
        ResultSet rs = null;
        String res = "";

        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM users;");
        rs = stmt.getResultSet();
        JSONArray response = ResultSetJsonMapper.convert(rs);
        res = response.toString();

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
        return res;
    }

    public static void addItem(Item item) throws SQLException, NamingException {
        PreparedStatement preparedStatement = null;

        String statement = "INSERT INTO cart ( productId , productName , quantity, userId ) VALUES ( ? , ? , ? , ? ); ";
        preparedStatement = conn.prepareStatement(statement) ;
        preparedStatement.setInt( 1 , item.getProductId() );
        preparedStatement.setString( 2 , item.getProductName() );
        preparedStatement.setInt( 3 , item.getQuantity() );
        preparedStatement.setInt( 4 , item.getUserId() );
        preparedStatement.executeUpdate();

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

    public static void addUser(String userName) throws SQLException, NamingException {
        PreparedStatement preparedStatement = null;

        String statement = "INSERT INTO users ( userName ) VALUES ( ? ); ";
        preparedStatement = conn.prepareStatement(statement) ;
        preparedStatement.setString( 1 , userName );
        preparedStatement.executeUpdate();

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

    public static void deleteUserById(int userId) throws SQLException, NamingException {
        PreparedStatement preparedStatement = null;

        String sql = "DELETE FROM users " +
        "WHERE userId = ?";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt( 1 , userId );
        int deleted = preparedStatement.executeUpdate();
        if (deleted == 0) {
            throw new SQLException("No user with the given id: "+Integer.toString(userId)+" exists.", Integer.toString(02000));
        }

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

        deleteItemsByUserId(userId);

    }

    public static void deleteItemsByUserId(int userId) throws SQLException, NamingException {
        PreparedStatement preparedStatement = null;

        String sql = "DELETE FROM cart " +
        "WHERE userId = ?";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt( 1 , userId );
        int deleted = preparedStatement.executeUpdate();
        if (deleted == 0) {
            //Ignore
        }

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

    public static void deleteItemById(int id) throws SQLException, NamingException {
        PreparedStatement preparedStatement = null;

        String sql = "DELETE FROM cart " +
        "WHERE id = ?";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt( 1 , id );
        int deleted = preparedStatement.executeUpdate();
        if (deleted == 0) {
            throw new SQLException("No item with the given id: "+Integer.toString(id)+" exists.", Integer.toString(02000));
        }

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

    public static void checkout(int userId) throws SQLException, NamingException {

        try{
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = null;
            String sql = "SELECT * FROM cart WHERE userId = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt( 1 , userId );
            ResultSet resultSet = preparedStatement.executeQuery();
            JSONArray response = ResultSetJsonMapper.convert(resultSet);
            Item[] items = new Gson().fromJson(response.toString(), Item[].class);
            
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException sqlEx) { } // ignore
    
                preparedStatement = null;
            }

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqlEx) { } // ignore
    
                resultSet = null;
            }

            for(Item item: items){
                String sqladd = "INSERT INTO activityLog ( userId , productId, productName , quantity ) VALUES ( ? , ? , ? , ? ); ";
                preparedStatement = conn.prepareStatement(sqladd);
                preparedStatement.setInt( 1 , userId );
                preparedStatement.setInt( 2 , item.getProductId() );
                preparedStatement.setString( 3 , item.getProductName() );
                preparedStatement.setInt( 4 , item.getQuantity() );
                preparedStatement.executeUpdate();

                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException sqlEx) { } // ignore
        
                    preparedStatement = null;
                }
                
            }

            String sqldelete = "DELETE FROM cart " +
            "WHERE userId = ?";
            preparedStatement = conn.prepareStatement(sqldelete);
            preparedStatement.setInt( 1 , userId );
            int deleted = preparedStatement.executeUpdate();
            if (deleted == 0) {
                //Ignore
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException sqlEx) { } // ignore

                preparedStatement = null;
            }

            // if everything is OK, commit the transaction
            conn.commit();
         
        } catch(SQLException e) {
            // in case of exception, rollback the transaction
            conn.rollback();
            throw new SQLException(e);
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException sqlEx) { } // ignore

            conn = null;
        }

    }


}