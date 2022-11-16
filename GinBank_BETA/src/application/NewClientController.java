package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class NewClientController implements Initializable{
	@FXML
	private TextField nameText;
	@FXML
	private TextField motherText;
	@FXML
	private TextField birthPlaceText;
	@FXML
	private TextArea addressText;
	@FXML
	private TextField emailText;
	@FXML
	private TextField phoneText;
	@FXML
	private DatePicker birthTimeText;
	@FXML
	private Label usernameLabel;
	
	
	private DatabaseConnection conn = new DatabaseConnection();
	
	public void insertIntoDatabase(ActionEvent event) {
		String sql;
		if(nameText.getText()==null && nameText.getText().equals("") 
				&& motherText.getText()==null && motherText.getText().equals("")
				&& birthPlaceText.getText()==null && birthPlaceText.getText().equals("")
				&& addressText.getText()==null && addressText.getText().equals("")
				&& emailText.getText()==null && emailText.getText().equals("")
				&& phoneText.getText()==null && phoneText.getText().equals("")
				&& birthTimeText.getValue().getYear()==0)
		{
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Nem minden mezõ került kitöltésre!");
	        a.setTitle("Hiba!");
	        a.show();
	        return;
		}
		else {
			sql="INSERT INTO customers values"
				+ "('"+nameText.getText()+"','"
				+ birthTimeText.getValue().getYear() + "-" + birthTimeText.getValue().getMonthValue() + "-" + birthTimeText.getValue().getDayOfMonth() + "',"
				+ "'"+ birthPlaceText.getText()+"',"
				+ "'" + motherText.getText()+"',"
				+ "'" + addressText.getText()+"',"
				+ "'" + emailText.getText()+"',"
				+ "'" + phoneText.getText()+"')";
		
			try {
				conn.insertIntoDatabase(sql);
				ResultSet rs = conn.doQuery("SELECT MAX(ID) FROM customers");
				rs.next();
				Alert a=new Alert(AlertType.CONFIRMATION);
		        a.setHeaderText("Ügyfél sikeresen hozzáadva. Azonosítója: "+rs.getInt(1));
		        a.setTitle("Rögzítve!");
		        a.show();
			} catch (SQLException e) {
				Alert a=new Alert(AlertType.ERROR);
		        a.setHeaderText("Nem sikerült az ügyfél rögzítése az adatbázisba! Ellenõrizd, hogy minden mezõbe megfelelõ adat került-e!");
		        a.setTitle("Hiba!");
		        a.show();
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usernameLabel.setText(SceneSwitcher.user);
	}

}
