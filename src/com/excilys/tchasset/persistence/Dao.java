package com.excilys.tchasset.persistence;
import java.sql.*;

public class Dao {
	public Connection conn;
    public static Dao instance;
    
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
		if (Dao.instance == null) {
			synchronized(Dao.class) {
				if (Dao.instance == null) {
					Dao.instance = new Dao();
	            }
	        }
		}
	    return Dao.instance;
    }
    
    
    
    public Connection getConn() {
		return conn;
	}
}
