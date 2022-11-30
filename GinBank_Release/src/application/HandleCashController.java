package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
/**
 * A sz�ml�n t�rt�n� p�nzelhelyez�st �s felv�telt kezel� oszt�ly
 * @author Hujber �d�m
 *
 */
public class HandleCashController implements Initializable{
	@FXML
	private Label usernameLabel;
	@FXML
	private TextField depositID;
	@FXML
	private TextField depositAmount;
	@FXML
	private TextField withdrawID;
	@FXML
	private TextField withdrawAmount;
	
	DatabaseConnection con;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usernameLabel.setText(SceneSwitcher.user);
		con=new DatabaseConnection();
	}
	/**
	 * P�nz befizet�st vez�rl� f�ggv�ny, ellen�rzi az adatok helyess�g�t, majd v�grehajtja a tranzakci�t a bankkal
	 */
	public void depositMoney() {
		if(depositID.getText().isEmpty()||depositID.getText().isBlank()||depositID.getText()==null
				||depositAmount.getText().isEmpty()||depositAmount.getText().isBlank()||depositAmount.getText()==null)
		{
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Nem t�lt�tte ki az �sszes mez�t!");
	        a.setTitle("Hiba!");
	        a.show();
	        return;
		}
		
		ResultSet rs=con.doQuery("select isActive from account where accountID="+depositID.getText());
		ResultSet rs2=con.doQuery("select balance from account where accountID=1000");
		try {
			rs.next();
			rs2.next();
			if(rs2.getInt(1)<Integer.parseInt(depositAmount.getText()))
			{
				Alert a=new Alert(AlertType.ERROR);
		        a.setHeaderText("A bank sz�ml�j�n nincs elegend� fedezet a teljes�t�shez.");
		        a.setTitle("Tranzakci� sikertelen!");
		        a.show();
		        return;
			}
			if(rs.getByte(1)==0)
			{
				Alert a=new Alert(AlertType.ERROR);
		        a.setHeaderText("Nem lehet p�nzt elhelyezni t�r�lt sz�ml�ra");
		        a.setTitle("Befizet�s visszautas�tva!");
		        a.show();
		        return;
			}
			//BANK id-j�t majd jav�tani adatb�zis reset ut�n
			String sql="update account set balance=balance+"+depositAmount.getText()+" where accountID="+depositID.getText();
			String sql2="update account set balance=balance-"+depositAmount.getText()+" where accountID=1000";
			String sql3="insert into transactions values("+depositAmount.getText()+",1000,"+depositID.getText()+",'"+LocalDateTime.now().getYear()+"-"+LocalDateTime.now().getMonthValue()+"-"+LocalDateTime.now().getDayOfMonth()+" "+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute()+":"+LocalDateTime.now().getSecond()+"')";
			ArrayList<String> SQL=new ArrayList<>();
			SQL.add(sql); SQL.add(sql2);SQL.add(sql3);
			con.processTransaction(SQL);
			Alert a=new Alert(AlertType.CONFIRMATION);
	        a.setHeaderText("A befizet�s sikeresen regisztr�lva lett a rendszerben.");
	        a.setTitle("Tranzakci� sikeres!");
	        a.show();
			
		} catch (SQLException e) {
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Elle�rizze, hogy minden adatot helyesen adott-e meg!");
	        a.setTitle("Befizet�s sikertelen!");
	        a.show();
			e.printStackTrace();
		}
	}
	
	/**
	 * P�nzfelv�telt kezel� f�ggv�ny. Ellen�rzi az adatok helyess�g�t, majd ha van fedezet r�gz�ti a tranzakci�t a bankkal.
	 */
	public void withdrawMoney() {
		if(withdrawID.getText().isEmpty()||withdrawID.getText().isBlank()||withdrawID.getText()==null
				||withdrawAmount.getText().isEmpty()||withdrawAmount.getText().isBlank()||withdrawAmount.getText()==null)
		{
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Nem t�lt�tte ki az �sszes mez�t!");
	        a.setTitle("Hiba!");
	        a.show();
	        return;
		}
		
		ResultSet rs=con.doQuery("select balance, isActive from account where accountID="+withdrawID.getText());
		try {
			rs.next();
			if(rs.getInt(1)<Integer.parseInt(withdrawAmount.getText())||rs.getByte(2)==0)
			{
				Alert a=new Alert(AlertType.ERROR);
		        a.setHeaderText("Az �gyf�l sz�ml�j�n nincs elegend� fedezet, vagy a sz�mla nem akt�v.");
		        a.setTitle("Tranzakci� sikertelen!");
		        a.show();
		        return;
			}
			
			//BANK id-j�t majd jav�tani adatb�zis reset ut�n
			String sql="update account set balance=balance-"+withdrawAmount.getText()+" where accountID="+withdrawID.getText();
			String sql2="update account set balance=balance+"+withdrawAmount.getText()+" where accountID=1000";
			String sql3="insert into transactions values("+withdrawAmount.getText()+","+withdrawID.getText()+",1000,'"+LocalDateTime.now().getYear()+"-"+LocalDateTime.now().getMonthValue()+"-"+LocalDateTime.now().getDayOfMonth()+" "+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute()+":"+LocalDateTime.now().getSecond()+"')";
			ArrayList<String> SQL=new ArrayList<>();
			SQL.add(sql); SQL.add(sql2);SQL.add(sql3);
			con.processTransaction(SQL);
			Alert a=new Alert(AlertType.CONFIRMATION);
	        a.setHeaderText("A befizet�s sikeresen regisztr�lva lett a rendszerben.");
	        a.setTitle("Tranzakci� sikeres!");
	        a.show();
			
		} catch (SQLException e) {
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Elle�rizze, hogy minden adatot helyesen adott-e meg!");
	        a.setTitle("Befizet�s sikertelen!");
	        a.show();
			e.printStackTrace();
		}
	}
	
}
