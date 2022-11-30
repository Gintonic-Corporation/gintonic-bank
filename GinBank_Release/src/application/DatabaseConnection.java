package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 * Ezen osztály fõ feladata az adatbázisszerverrel történõ kommunikáció biztosítása. Ehhez van egy belsõ connection és statement objektuma. A kapcsolat létrehozásához szükséges paraméter stringet is külön tárolja. 
 * @author Hujber Ádám
 */
public class DatabaseConnection {
	private Connection con;
	private Statement stmt;
	private String url = "jdbc:sqlserver://DESKTOP-NS687T6:1433; databaseName=GINTONIC_BANK; encrypt=true; trustServerCertificate=true; integratedSecurity=true;";
	/**
	 * A konstruktor létrehozza a kapcsolatot az adatbázisszerverrel és inicializálja a két objektumát.
	 */
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
	/**
	 * Fügvény végrehajt egy lekérdezést az adatbázison. Paraméterben kapja meg az SQL utasítást, majd a lekérdezés eredményét ResultSetben visszaadja. Ha nincs találat, üres adatszerkezetet ad vissza.
	 * @param SQL String, a query szövege
	 * @return ResultSet, a lekérdezés eredményét tartalmazó adatszerkezet
	 */
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
	/**
	 * Ez a függvény az adatbázisba történõ beillesztést kezeli. Paraméterben megkapja az utasítást és végrehajtja. Hiba esetén SQLExceptiont dob.
	 * @param SQL String, az insert utasítás
	 * @throws SQLException Ha valamilyen okból sikertelen a beillesztés
	 */
	public void insertIntoDatabase(String SQL) throws SQLException {
		stmt.execute(SQL);
	}
	/**
	 * Ez a függvény az adatbázisba történõ frissítést kezeli. Paraméterben megkapja az utasítást és végrehajtja. Hiba esetén SQLExceptiont dob.
	 * @param SQL String, az update utasítás
	 * @throws SQLException Ha valamilyen okból sikertelen a módosítás
	 */
	public void updateDatabase(String SQL) throws SQLException {
		stmt.executeUpdate(SQL);
	}
	/**
	 * Ez a függvény a tranzakciókat kezeli. Egybe foglalja a különbözõ utasításokat, és egy egységként végrehajtja, hiba esetén pedig visszaállítja az eredeti állapotot.
	 * @param SQL String lista, a végrehajtandó utasítások listája
	 * @throws SQLException Ha hiba volt, akkor azt jelzi a hívó folyamatnak
	 */
	public void processTransaction(ArrayList<String> SQL) throws SQLException {
		 try {
			 con.setAutoCommit(false);
			 for (int i = 0; i < SQL.size(); i++) {
				stmt.execute(SQL.get(i));
			 }
			 con.commit();
		 }
		 catch(SQLException e) {
			 con.rollback();
			 throw new SQLException();
			 //e.printStackTrace();
		 }finally {
			 con.setAutoCommit(true);
		 }
		 
	}
	
	public void deletRow(String sql) throws SQLException {
		stmt.execute(sql);
	}

}
