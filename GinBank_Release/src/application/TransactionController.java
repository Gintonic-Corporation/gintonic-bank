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
 * Átutalások kezdeményezését kezelõ osztály
 * @author Hujber Ádám
 *
 */
public class TransactionController implements Initializable{
	@FXML
	private Label usernameLabel;
	@FXML
	private TextField senderAcc;
	@FXML
	private TextField receiverAcc;
	@FXML
	private TextField receiverName;
	@FXML
	private TextField transferAmount;
	
	DatabaseConnection con;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usernameLabel.setText(SceneSwitcher.user);
		con=new DatabaseConnection();
	}
	/**
	 * Átutalást végrehajtó függvény.
	 */
	public void transferMoney() {
		if(senderAcc.getText().isEmpty()||senderAcc.getText().isBlank()||senderAcc.getText()==null
				||receiverAcc.getText().isEmpty()||receiverAcc.getText().isBlank()||receiverAcc.getText()==null
				||receiverName.getText().isEmpty()||receiverName.getText().isBlank()||receiverName.getText()==null
				||transferAmount.getText().isEmpty()||transferAmount.getText().isBlank()||transferAmount.getText()==null)
		{
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Nem töltötte ki az összes mezõt!");
	        a.setTitle("Hiba!");
	        a.show();
	        return;
		}
		ResultSet rs=con.doQuery("select balance, isActive from account where accountID="+senderAcc.getText());
		try {
			rs.next();
			if(rs.getInt(1)<Integer.parseInt(transferAmount.getText())||rs.getByte(2)==0)
			{
				Alert a=new Alert(AlertType.ERROR);
		        a.setHeaderText("Az ügyfél számláján nincs elegendõ fedezet, vagy a számla nem aktív.");
		        a.setTitle("Tranzakció sikertelen!");
		        a.show();
		        return;
			}
			else
			{
				String sql="update account set balance=balance+"+transferAmount.getText()+" where accountID="+receiverAcc.getText()+" and owner in (select ID from customers where realname='"+receiverName.getText()+"')";
				String sql2="update account set balance=balance-"+transferAmount.getText()+" where accountID="+senderAcc.getText();
				String sql3="insert into transactions values("+transferAmount.getText()+","+senderAcc.getText()+","+receiverAcc.getText()+",'"+LocalDateTime.now().getYear()+"-"+LocalDateTime.now().getMonthValue()+"-"+LocalDateTime.now().getDayOfMonth()+" "+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute()+":"+LocalDateTime.now().getSecond()+"')";
				ArrayList<String> SQL=new ArrayList<>();
				SQL.add(sql); SQL.add(sql2);SQL.add(sql3);
				con.processTransaction(SQL);
				Alert a=new Alert(AlertType.CONFIRMATION);
		        a.setHeaderText("Az átutalás sikeresen regisztrálva lett a rendszerben.");
		        a.setTitle("Tranzakció sikeres!");
		        a.show();
			}
		} catch (SQLException e) {
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Elleõrizze, hogy minden adatot helyesen adott-e meg!");
	        a.setTitle("Tranzakció sikertelen!");
	        a.show();
			e.printStackTrace();
		}
		
		
		
	}

}
