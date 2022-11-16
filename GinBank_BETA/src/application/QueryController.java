package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn;

public class QueryController implements Initializable{
	@FXML
	private Label usernameLabel;
	@FXML
	private TableView<Customer> customerTable;
	@FXML
	private TableColumn<Customer, Integer> ID_Coll;
	@FXML
	private TableColumn<Customer, String> name_Coll;
	@FXML
	private TableColumn<Customer, String> mother_Coll;
	@FXML
	private TableColumn<Customer, String> bplace_Coll;
	@FXML
	private TableColumn<Customer, String> bdate_Coll;
	@FXML
	private TableColumn<Customer, String> haddress_Coll;
	@FXML
	private TableColumn<Customer, String> email_Coll;
	@FXML
	private TableColumn<Customer, String> phone_Coll;
	@FXML
	private TextField searchKW;
	@FXML
	private TextField searchID;
	@FXML
	private TextField searchAccount;
	
	DatabaseConnection con;
	ObservableList<Customer> obserList= FXCollections.observableArrayList();
	
	
	public void searchToModify() {
		String sql;
		if(!(searchID.getText().isEmpty()||searchID.getText().isBlank()||searchID.getText()==null))
			sql="SELECT ID, realname, birthdate, birthplace, mother, homeaddress, email, phone FROM customers where ID="+searchID.getText();
		else if(!(searchAccount.getText().isEmpty()||searchAccount.getText().isBlank()||searchAccount.getText()==null))
			sql="select ID, realname, birthdate, birthplace, mother, homeaddress, email, phone from customers join account on account.owner=customers.ID where accountID="+searchAccount.getText();
		else {
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Nem adta meg az ügyfélazonosítót vagy számlaszámot!");
	        a.setTitle("Hiba!");
	        a.show();
	        return;
		}
		ResultSet rs=con.doQuery(sql);
		
		try {
			rs.next();
			Integer ID=rs.getInt(1);
			Dialog<Customer> dialog = new Dialog<>();
			dialog.setTitle("Ügyféladat módosítása");
			dialog.setHeaderText("Írja át a megfelelõ mezõket a "+ID+" azonosítójú ügyfélnek");
			DialogPane dialogPane = dialog.getDialogPane();
			dialogPane.getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
			TextField nameText= new TextField(rs.getString(2));
			DatePicker birthdateText = new DatePicker(rs.getDate(3).toLocalDate());
			TextField birthplaceText= new TextField(rs.getString(4));
			TextField motherText= new TextField(rs.getString(5));
			TextField addressText= new TextField(rs.getString(6));
			TextField emailText= new TextField(rs.getString(7));
			TextField phoneText= new TextField(rs.getString(8));
			
			dialogPane.setContent(new VBox(8,nameText,motherText,birthdateText,birthplaceText,addressText,emailText,phoneText));
			dialog.show();
			dialog.setResultConverter((ButtonType button) -> {
	            if (button == ButtonType.OK) {
	                String bdate=birthdateText.getValue().getYear() + "-" + birthdateText.getValue().getMonthValue() + "-" + birthdateText.getValue().getDayOfMonth();
	                String updateQuery="update customers set realname='"+nameText.getText()+"', birthdate='"+bdate+"',birthplace='"+birthplaceText.getText()+"',mother='"+motherText.getText()+"',homeaddress='"+addressText.getText()+"',email='"+emailText.getText()+"',phone='"+phoneText.getText()+"' where ID="+ID;
	                try {
						con.updateDatabase(updateQuery);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	            return null;
	        });
		} catch (SQLException e) {
			Alert a=new Alert(AlertType.WARNING);
	        a.setHeaderText("Nem található az adatoknak megfelelõ ügyfél!");
	        a.setTitle("Hiba!");
	        a.show();
			e.printStackTrace();
		}
		
	}
	public void refreshList() {
		obserList.clear();
		customerTable.getItems().clear();
		String SQL = "SELECT ID, realname, birthdate, birthplace, mother, homeaddress, email, phone FROM customers where realname != 'BANK'";
		ResultSet rs=con.doQuery(SQL);
		try {
			while(rs.next())
			{
				Integer qID=rs.getInt(1);
				String qbirthdate=rs.getDate(3).toString();
				obserList.add(new Customer(qID,rs.getString(2),qbirthdate,rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)));
			}
			
			ID_Coll.setCellValueFactory(new PropertyValueFactory<>("id"));
			name_Coll.setCellValueFactory(new PropertyValueFactory<>("name"));
			mother_Coll.setCellValueFactory(new PropertyValueFactory<>("mother"));
			bplace_Coll.setCellValueFactory(new PropertyValueFactory<>("bplace"));
			bdate_Coll.setCellValueFactory(new PropertyValueFactory<>("bdate"));
			haddress_Coll.setCellValueFactory(new PropertyValueFactory<>("haddress"));
			email_Coll.setCellValueFactory(new PropertyValueFactory<>("email"));
			phone_Coll.setCellValueFactory(new PropertyValueFactory<>("phone"));
			
			customerTable.setItems(obserList);
			
			FilteredList<Customer> filteredList = new FilteredList<>(obserList, b -> true);
			
			searchKW.textProperty().addListener((observable, oldValue,newValue) -> {
				filteredList.setPredicate(Customer -> {
					if(newValue.isEmpty()||newValue.isBlank()||newValue==null) {
						return true;
					}
					
					String searchKeyword = newValue.toLowerCase();
					
					if(Customer.getName().toLowerCase().indexOf(searchKeyword) > -1) {
						return true;
					}else if(Customer.getId().toString().indexOf(searchKeyword) > -1) {
						return true;
					}else if(Customer.getBdate().toLowerCase().indexOf(searchKeyword) > -1) {
						return true;
					}else if(Customer.getBplace().toLowerCase().indexOf(searchKeyword) > -1) {
						return true;
					}else if(Customer.getMother().toLowerCase().indexOf(searchKeyword) > -1) {
						return true;
					}else if(Customer.getHaddress().toLowerCase().indexOf(searchKeyword) > -1) {
						return true;
					}else if(Customer.getEmail().toLowerCase().indexOf(searchKeyword) > -1) {
						return true;
					}else if(Customer.getPhone().toLowerCase().indexOf(searchKeyword) > -1) {
						return true;
					}else return false;
				});
			});
			
			SortedList<Customer> sortedList= new SortedList<>(filteredList);
			
			sortedList.comparatorProperty().bind(customerTable.comparatorProperty());
			
			customerTable.setItems(sortedList);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usernameLabel.setText(SceneSwitcher.user);
		
		con = new DatabaseConnection();
		
		refreshList();
	}
	
	

}
