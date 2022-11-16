package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class NewAccountController implements Initializable{
	@FXML
	private Label usernameLabel;
	@FXML
	private TextField custAzon;
	@FXML
	private TextField accAzon;
	@FXML
	private TextField customerToAcc;
	@FXML
	private TableView<Data> accountsTable;
	@FXML
	private TableColumn<Data, Integer> accID;
	@FXML
	private TableColumn<Data, Integer> balance;
	
	DatabaseConnection con;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usernameLabel.setText(SceneSwitcher.user);
		con=new DatabaseConnection();
	}
	
	public void createAccount() {
		if(custAzon.getText().isEmpty()||custAzon.getText().isBlank()||custAzon.getText()==null)
		{
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Nem adta meg az �gyf�lazonos�t�t!");
	        a.setTitle("Hiba!");
	        a.show();
	        return;
		}
		String sql="insert into account (owner) values("+custAzon.getText()+")";
		try {
			con.insertIntoDatabase(sql);
			ResultSet rs=con.doQuery("SELECT max(accountID) from account");
			rs.next();
			Alert a=new Alert(AlertType.CONFIRMATION);
	        a.setHeaderText("Sz�mla sikeresen l�trehozva. Hozz� tartoz� sz�mlasz�m: "+rs.getInt(1));
	        a.setTitle("Sz�mla l�trehozva!");
	        a.show();
		} catch (SQLException e) {
			Alert a=new Alert(AlertType.WARNING);
	        a.setHeaderText("Nem tal�lhat� �gyf�l a megadott �gyf�lsz�mmal!");
	        a.setTitle("Hiba!");
	        a.show();
		}
	}
	
	public void deactivateAccount() {
		if(accAzon.getText().isEmpty()||accAzon.getText().isBlank()||accAzon.getText()==null)
		{
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Nem adta meg a sz�mlasz�mot!");
	        a.setTitle("Hiba!");
	        a.show();
	        return;
		}
		String sql="update account set isActive=0 where accountID="+accAzon.getText();
		try {
			con.updateDatabase(sql);
			Alert a=new Alert(AlertType.CONFIRMATION);
	        a.setHeaderText("Sz�mla sikeresen deaktiv�lva.");
	        a.setTitle("Sz�mla t�r�lve!");
	        a.show();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Alert a=new Alert(AlertType.WARNING);
	        a.setHeaderText("Nem siker�lt v�grehajtani a sz�mla deaktiv�l�s�t. Ellen�rizze, hogy helyes sz�mlasz�mot adott-e meg.");
	        a.setTitle("T�rl�s sikertelen!");
	        a.show();
			e.printStackTrace();
		}
	}
	
	public void searchForAccounts() {
		if(customerToAcc.getText().isEmpty()||customerToAcc.getText().isBlank()||customerToAcc.getText()==null)
		{
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Nem adta meg az �gyf�lsz�mot!");
	        a.setTitle("Hiba!");
	        a.show();
	        return;
		}
		ResultSet rs=con.doQuery("SELECT accountID,balance from account where owner="+customerToAcc.getText());
		ObservableList<Data> obserList=FXCollections.observableArrayList();
		try {
			while(rs.next())
			{
				obserList.add(new Data(rs.getInt(1),rs.getInt(2)));
			}
			accID.setCellValueFactory(new PropertyValueFactory<>("D_accID"));
			balance.setCellValueFactory(new PropertyValueFactory<>("D_balance"));
			accountsTable.setItems(obserList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
