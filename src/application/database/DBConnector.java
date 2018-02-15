package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

	private String user = "root";
	private String pass = "B43stieBoy5";
	
	public Connection connection() throws SQLException {
		
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/testfx?useSSL=False", user, pass);
	}
	
	
}
