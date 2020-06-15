package com.excilys.tchasset.persistence;
import java.sql.*;

import com.excilys.tchasset.log.Logging;

public class Dao implements AutoCloseable {
	private Connection conn;
    public static Dao instance;
    
    private Dao() {}

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
		try {
			conn = HikariCP.getConnection();
		} catch (SQLException e) {
			Logging.writeFile(e.getMessage());
		}
    	return conn;
	}

	@Override
	public void close() throws Exception {
		getConn().close();
	}
}
