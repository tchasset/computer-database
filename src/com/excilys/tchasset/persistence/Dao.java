package com.excilys.tchasset.persistence;
import java.sql.*;

public class Dao {
	public Connection conn;
    private Statement statement;
    public static Dao db;
    
    private Dao() {
        String url= "jdbc:mysql://localhost:3306/";
        String dbName = "computer-database-db";
        String driver = "com.mysql.cj.jdbc.Driver";
        String userName = "admincdb";
        String password = "qwerty1234";
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection)DriverManager.getConnection(url+dbName,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    public static final Dao getInstance() {
        if ( db == null ) {
            db = new Dao();
        }
        return db;
    }
    
    public ResultSet execute(String query) throws SQLException{
        statement = db.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }

    public int update(String insertQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;
 
    }
}
