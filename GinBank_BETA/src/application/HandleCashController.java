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
	
	public void depositMoney() {
		if(depositID.getText().isEmpty()||depositID.getText().isBlank()||depositID.getText()==null
				||depositAmount.getText().isEmpty()||depositAmount.getText().isBlank()||depositAmount.getText()==null)
		{
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Nem töltötte ki az összes mezõt!");
	        a.setTitle("Hiba!");
	        a.show();
	        return;
		}
		//Tranzakcióhoz hasonlóan befejezni de a bank adataival tölti ki
		
		ResultSet rs=con.doQuery("select isActive from account where accountID="+depositID.getText());
		try {
			rs.next();
			if(rs.getByte(1)==0)
			{
				Alert a=new Alert(AlertType.ERROR);
		        a.setHeaderText("Nem lehet pénzt elhelyezni törölt számlára");
		        a.setTitle("Befizetés visszautasítva!");
		        a.show();
		        return;
			}
			//BANK id-jét majd javítani adatbázis reset után
			String sql="update account set balance=balance+"+depositAmount.getText()+" where accountID="+depositID.getText();
			String sql2="update account set balance=balance-"+depositAmount.getText()+" where accountID=1373";
			String sql3="insert into transactions values("+depositAmount.getText()+",1373,"+depositID.getText()+",'"+LocalDateTime.now().getYear()+"-"+LocalDateTime.now().getMonthValue()+"-"+LocalDateTime.now().getDayOfMonth()+" "+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute()+":"+LocalDateTime.now().getSecond()+"')";
			ArrayList<String> SQL=new ArrayList<>();
			SQL.add(sql); SQL.add(sql2);SQL.add(sql3);
			con.processTransaction(SQL);
			Alert a=new Alert(AlertType.CONFIRMATION);
	        a.setHeaderText("A befizetés sikeresen regisztrálva lett a rendszerben.");
	        a.setTitle("Tranzakció sikeres!");
	        a.show();
			
		} catch (SQLException e) {
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Elleõrizze, hogy minden adatot helyesen adott-e meg!");
	        a.setTitle("Befizetés sikertelen!");
	        a.show();
			e.printStackTrace();
		}
	}
	
	
	public void withdrawMoney() {
		if(withdrawID.getText().isEmpty()||withdrawID.getText().isBlank()||withdrawID.getText()==null
				||withdrawAmount.getText().isEmpty()||withdrawAmount.getText().isBlank()||withdrawAmount.getText()==null)
		{
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Nem töltötte ki az összes mezõt!");
	        a.setTitle("Hiba!");
	        a.show();
	        return;
		}
		//Tranzakcióhoz hasonlóan befejezni de a bank adataival tölti ki
		
		ResultSet rs=con.doQuery("select balance, isActive from account where accountID="+withdrawID.getText());
		try {
			rs.next();
			if(rs.getInt(1)<Integer.parseInt(withdrawAmount.getText())||rs.getByte(2)==0)
			{
				Alert a=new Alert(AlertType.ERROR);
		        a.setHeaderText("Az ügyfél számláján nincs elegendõ fedezet, vagy a számla nem aktív.");
		        a.setTitle("Tranzakció sikertelen!");
		        a.show();
		        return;
			}
			
			//BANK id-jét majd javítani adatbázis reset után
			String sql="update account set balance=balance-"+withdrawAmount.getText()+" where accountID="+withdrawID.getText();
			String sql2="update account set balance=balance+"+withdrawAmount.getText()+" where accountID=1373";
			String sql3="insert into transactions values("+withdrawAmount.getText()+","+withdrawID.getText()+",1373,'"+LocalDateTime.now().getYear()+"-"+LocalDateTime.now().getMonthValue()+"-"+LocalDateTime.now().getDayOfMonth()+" "+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute()+":"+LocalDateTime.now().getSecond()+"')";
			ArrayList<String> SQL=new ArrayList<>();
			SQL.add(sql); SQL.add(sql2);SQL.add(sql3);
			con.processTransaction(SQL);
			Alert a=new Alert(AlertType.CONFIRMATION);
	        a.setHeaderText("A befizetés sikeresen regisztrálva lett a rendszerben.");
	        a.setTitle("Tranzakció sikeres!");
	        a.show();
			
		} catch (SQLException e) {
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Elleõrizze, hogy minden adatot helyesen adott-e meg!");
	        a.setTitle("Befizetés sikertelen!");
	        a.show();
			e.printStackTrace();
		}
	}
	
}
