package com.example.demo;

import java.sql.*;
import org.json.JSONArray;

public class Repository {

    private static Connection conn = null;

    private static void init() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restdb?" + "user=root&password=admin");

        String sql = "CREATE TABLE IF NOT EXISTS cart ( \n" +
        "  id INT NOT NULL PRIMARY KEY , \n" +
        "  name VARCHAR(100) NOT NULL , \n" +
        "  quantity INT NOT NULL \n" +
        ");";

        Statement stmt = conn.createStatement();
        stmt.execute( sql );

    }

    public static String getCart() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        String res = "";

        init();

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

    public static void deleteItemById(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        init();

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

    public static void addItem(Item item) throws SQLException {
        PreparedStatement preparedStatement = null;
        init();

        String statement = "INSERT INTO cart ( id , name , quantity ) VALUES ( ? , ? , ? ); ";
        preparedStatement = conn.prepareStatement(statement) ;
        preparedStatement.setInt( 1 , item.getId() );
        preparedStatement.setString( 2 , item.getName() );
        preparedStatement.setInt( 3 , item.getQuantity() );
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

}