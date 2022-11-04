package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SceneSwitcher implements Initializable{
	
	@FXML
	private Label usernameLabel;
	
	 private Stage stage;
	 private Scene scene;
	 private Parent root;
	 public static String user;
	 
	 @FXML
	 public void switchToMenu(ActionEvent event) {
	  try {
		root = FXMLLoader.load(getClass().getResource("home.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		  e.printStackTrace();
	  }
	 }
	 
	 public void setUser(String u) {
		 user=u;
	 }
	 
	 public void switchToNewClient() {
		  try {
			  Parent root1 = FXMLLoader.load(getClass().getResource("NewClient.fxml"));
			  Scene scene1 = new Scene(root1);
			  Stage newStage=new Stage();
			  newStage.setScene(scene1);
			  newStage.show();
			  newStage.centerOnScreen();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	}
	 
	 public void switchToQuery() {
		  try {
			  Parent root1 = FXMLLoader.load(getClass().getResource("Query.fxml"));
			  Scene scene1 = new Scene(root1);
			  Stage newStage=new Stage();
			  newStage.setScene(scene1);
			  newStage.show();
			  newStage.centerOnScreen();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	}
	 
	 public void switchToNewAccount() {
		  try {
			  Parent root1 = FXMLLoader.load(getClass().getResource("NewAccount.fxml"));
			  Scene scene1 = new Scene(root1);
			  Stage newStage=new Stage();
			  newStage.setScene(scene1);
			  newStage.show();
			  newStage.centerOnScreen();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	}
	 
	 public void switchToTransaction() {
		  try {
			  Parent root1 = FXMLLoader.load(getClass().getResource("Transaction.fxml"));
			  Scene scene1 = new Scene(root1);
			  Stage newStage=new Stage();
			  newStage.setScene(scene1);
			  newStage.show();
			  newStage.centerOnScreen();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	}
	 
	 public void switchToTransactionLog() {
		  try {
			  Parent root1 = FXMLLoader.load(getClass().getResource("TransactionLog.fxml"));
			  Scene scene1 = new Scene(root1);
			  Stage newStage=new Stage();
			  newStage.setScene(scene1);
			  newStage.show();
			  newStage.centerOnScreen();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	}
	 
	 public void switchToHandleCash() {
		  try {
			  Parent root1 = FXMLLoader.load(getClass().getResource("HandleCash.fxml"));
			  Scene scene1 = new Scene(root1);
			  Stage newStage=new Stage();
			  newStage.setScene(scene1);
			  newStage.show();
			  newStage.centerOnScreen();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usernameLabel.setText(user);
	}
}
