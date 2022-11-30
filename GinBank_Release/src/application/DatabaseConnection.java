package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 * Ezen oszt�ly f� feladata az adatb�zisszerverrel t�rt�n� kommunik�ci� biztos�t�sa. Ehhez van egy bels� connection �s statement objektuma. A kapcsolat l�trehoz�s�hoz sz�ks�ges param�ter stringet is k�l�n t�rolja. 
 * @author Hujber �d�m
 */
public class DatabaseConnection {
	private Connection con;
	private Statement stmt;
	private String url = "jdbc:sqlserver://DESKTOP-NS687T6:1433; databaseName=GINTONIC_BANK; encrypt=true; trustServerCertificate=true; integratedSecurity=true;";
	/**
	 * A konstruktor l�trehozza a kapcsolatot az adatb�zisszerverrel �s inicializ�lja a k�t objektum�t.
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
	 * F�gv�ny v�grehajt egy lek�rdez�st az adatb�zison. Param�terben kapja meg az SQL utas�t�st, majd a lek�rdez�s eredm�ny�t ResultSetben visszaadja. Ha nincs tal�lat, �res adatszerkezetet ad vissza.
	 * @param SQL String, a query sz�vege
	 * @return ResultSet, a lek�rdez�s eredm�ny�t tartalmaz� adatszerkezet
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
	 * Ez a f�ggv�ny az adatb�zisba t�rt�n� beilleszt�st kezeli. Param�terben megkapja az utas�t�st �s v�grehajtja. Hiba eset�n SQLExceptiont dob.
	 * @param SQL String, az insert utas�t�s
	 * @throws SQLException Ha valamilyen okb�l sikertelen a beilleszt�s
	 */
	public void insertIntoDatabase(String SQL) throws SQLException {
		stmt.execute(SQL);
	}
	/**
	 * Ez a f�ggv�ny az adatb�zisba t�rt�n� friss�t�st kezeli. Param�terben megkapja az utas�t�st �s v�grehajtja. Hiba eset�n SQLExceptiont dob.
	 * @param SQL String, az update utas�t�s
	 * @throws SQLException Ha valamilyen okb�l sikertelen a m�dos�t�s
	 */
	public void updateDatabase(String SQL) throws SQLException {
		stmt.executeUpdate(SQL);
	}
	/**
	 * Ez a f�ggv�ny a tranzakci�kat kezeli. Egybe foglalja a k�l�nb�z� utas�t�sokat, �s egy egys�gk�nt v�grehajtja, hiba eset�n pedig vissza�ll�tja az eredeti �llapotot.
	 * @param SQL String lista, a v�grehajtand� utas�t�sok list�ja
	 * @throws SQLException Ha hiba volt, akkor azt jelzi a h�v� folyamatnak
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
