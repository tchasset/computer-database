package com.excilys.tchasset.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCP {

	private static HikariConfig config = new HikariConfig("/hikari.properties");
	private static HikariDataSource ds = new HikariDataSource(config);
 
    private HikariCP() {}
 
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
   
}
