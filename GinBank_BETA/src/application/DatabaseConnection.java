package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
	
	public void insertIntoDatabase(String SQL) throws SQLException {
		stmt.execute(SQL);
	}
	
	public void updateDatabase(String SQL) throws SQLException {
		stmt.executeUpdate(SQL);
	}
	public void processTransaction(ArrayList<String> SQL) throws SQLException {
		 /*stmt.executeUpdate(SQL.get(0));
		 stmt.executeUpdate(SQL.get(1));
		 stmt.execute(SQL.get(2));*/
		 try {
			 con.setAutoCommit(false);
			 for (int i = 0; i < SQL.size(); i++) {
				stmt.execute(SQL.get(i));
			 }
			 con.commit();
		 }
		 catch(SQLException e) {
			 con.rollback();
			 e.printStackTrace();
		 }finally {
			 con.setAutoCommit(true);
		 }
		 
	}

}
