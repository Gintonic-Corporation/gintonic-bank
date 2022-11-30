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
 * �tutal�sok kezdem�nyez�s�t kezel� oszt�ly
 * @author Hujber �d�m
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
	 * �tutal�st v�grehajt� f�ggv�ny.
	 */
	public void transferMoney() {
		if(senderAcc.getText().isEmpty()||senderAcc.getText().isBlank()||senderAcc.getText()==null
				||receiverAcc.getText().isEmpty()||receiverAcc.getText().isBlank()||receiverAcc.getText()==null
				||receiverName.getText().isEmpty()||receiverName.getText().isBlank()||receiverName.getText()==null
				||transferAmount.getText().isEmpty()||transferAmount.getText().isBlank()||transferAmount.getText()==null)
		{
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Nem t�lt�tte ki az �sszes mez�t!");
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
		        a.setHeaderText("Az �gyf�l sz�ml�j�n nincs elegend� fedezet, vagy a sz�mla nem akt�v.");
		        a.setTitle("Tranzakci� sikertelen!");
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
		        a.setHeaderText("Az �tutal�s sikeresen regisztr�lva lett a rendszerben.");
		        a.setTitle("Tranzakci� sikeres!");
		        a.show();
			}
		} catch (SQLException e) {
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Elle�rizze, hogy minden adatot helyesen adott-e meg!");
	        a.setTitle("Tranzakci� sikertelen!");
	        a.show();
			e.printStackTrace();
		}
		
		
		
	}

}
