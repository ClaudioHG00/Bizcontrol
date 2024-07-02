package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connessione {
	
	//JDBC driver and db url
	static final String JDBC_DRIVER ="com.mysql.jdbc.Driver";
	static final String DB_URL ="jdbc:mysql://localhost:3306/bizcontrol";
	static final String UserDB ="root";
	static final String PasswordDB ="";
	
	
	private static Connection conn = null;
	
	
	static {
		try {
			Class.forName(JDBC_DRIVER);
			conn=DriverManager.getConnection(DB_URL, UserDB, PasswordDB);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Connection getConnection()
	{
		return conn;
	}

}
