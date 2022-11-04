package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	private Connection con;
	private Statement stmt;
	private String url = "jdbc:sqlserver://DESKTOP-NS687T6:1433; databaseName=GINTONIC_BANK; encrypt=true; trustServerCertificate=true; integratedSecurity=true;";

	DatabaseConnection()
	{
		try {
			con=DriverManager.getConnection(url);
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection()
	{
		return con;
	}
	public ResultSet doQuery(String SQL) {
		ResultSet rs;
		try {
			rs=stmt.executeQuery(SQL);
		} catch (SQLException e) {
			rs=null;
			e.printStackTrace();
		}
		return rs;
	}
	
	public void insertNewCustomer(String SQL) throws SQLException {
			stmt.execute(SQL);
	}

}
