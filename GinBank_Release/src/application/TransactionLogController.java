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
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
/**
 * Számlatörténet lekérdezést végzõ osztály
 * @author Hujber Ádám
 *
 */
public class TransactionLogController implements Initializable{
	@FXML
	private Label usernameLabel;
	@FXML
	private TextField accID;
	@FXML
	private DatePicker fromDate;
	@FXML
	private TableView<DataOfTransaction> logTable;
	@FXML
	private TableColumn<DataOfTransaction,Integer> sender;
	@FXML
	private TableColumn<DataOfTransaction,Integer> receiver;
	@FXML
	private TableColumn<DataOfTransaction,Integer> amount;
	@FXML
	private TableColumn<DataOfTransaction,String> dateColl;
	
	DatabaseConnection con;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usernameLabel.setText(SceneSwitcher.user);
		con=new DatabaseConnection();
	}
	/**
	 * Számlatörténetet lekérdezõ függvény. Lekéri egy számlának a kimenõ és bejövõ tranzakcióit és táblázatba tölti.
	 */
	public void searchTransactionLog() {
		if(accID.getText().isEmpty()||accID.getText().isBlank()||accID.getText()==null)
		{
			Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Nem adta meg az számlaszámot!");
	        a.setTitle("Hiba!");
	        a.show();
	        return;
		}
		int accountIDinInt=Integer.parseInt(accID.getText());
		String date="";
		if(fromDate.getValue()!=null)
			date=" and transactiondate>'"+fromDate.getValue().getYear()+"-"+fromDate.getValue().getMonthValue()+"-"+fromDate.getValue().getDayOfMonth()+"'";
		
		String sql="select sender, receiver, amount, transactiondate from transactions where (sender="+accID.getText()+" or receiver="+accID.getText()+")"+date;
		
		ResultSet rs=con.doQuery(sql);
		ObservableList<DataOfTransaction> obserList=FXCollections.observableArrayList();
		try {
			while(rs.next())
			{
				obserList.add(new DataOfTransaction(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4)));
			}
			sender.setCellValueFactory(new PropertyValueFactory<>("sender"));
			receiver.setCellValueFactory(new PropertyValueFactory<>("receiver"));
			amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
			dateColl.setCellValueFactory(new PropertyValueFactory<>("date"));
			logTable.setItems(obserList);
			
			logTable.setRowFactory(tv -> new TableRow<DataOfTransaction>() {
			    @Override
			    protected void updateItem(DataOfTransaction item, boolean empty) {
			        super.updateItem(item, empty);
			        if (item == null)
			            setStyle("");
			        else if (item.getReceiver() == accountIDinInt)
			            setStyle("-fx-background-color: #1bf702;");
			        else if (item.getSender() == accountIDinInt)
			            setStyle("-fx-background-color: #f70202;");
			        /*else
			            setStyle("");*/
			    }
			});
			logTable.refresh();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
