package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneSwitcher implements Initializable{
	
	@FXML
	private Label usernameLabel;
	@FXML
	private Button userAdd;
	@FXML
	private Button pwdModify;
	
	 private Stage stage;
	 private Scene scene;
	 private Parent root;
	 public static String user;
	 public static boolean isadmin;
	 
	 @FXML
	 public void switchToMenu(Event event) {
	  try {
		root = FXMLLoader.load(getClass().getResource("home.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Kezdõlap");
		stage.show();
		stage.centerOnScreen();
		
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		  e.printStackTrace();
	  }
	 }
	 
	 public void setUser(String u,boolean a) {
		 user=u;
		 isadmin=a;
	 }
	 
	 public void switchToNewClient() {
		  try {
			  Parent root1 = FXMLLoader.load(getClass().getResource("NewClient.fxml"));
			  Scene scene1 = new Scene(root1);
			  Stage newStage=new Stage();
			  newStage.setScene(scene1);
			  newStage.getIcons().add(new Image(SceneSwitcher.class.getClassLoader().getResourceAsStream("GintonicCorpLogo.png")));
			  newStage.setTitle("Új ügyfél");
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
			  newStage.getIcons().add(new Image(SceneSwitcher.class.getClassLoader().getResourceAsStream("GintonicCorpLogo.png")));
			  newStage.setTitle("Ügyfél adatok");
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
			  newStage.getIcons().add(new Image(SceneSwitcher.class.getClassLoader().getResourceAsStream("GintonicCorpLogo.png")));
			  newStage.setTitle("Számla nyitás/zárás");
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
			  newStage.getIcons().add(new Image(SceneSwitcher.class.getClassLoader().getResourceAsStream("GintonicCorpLogo.png")));
			  newStage.setTitle("Átutalás");
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
			  newStage.getIcons().add(new Image(SceneSwitcher.class.getClassLoader().getResourceAsStream("GintonicCorpLogo.png")));
			  newStage.setTitle("Számlatörténet");
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
			  newStage.getIcons().add(new Image(SceneSwitcher.class.getClassLoader().getResourceAsStream("GintonicCorpLogo.png")));
			  newStage.setTitle("Befizetés/Kifizetés");
			  newStage.show();
			  newStage.centerOnScreen();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	}
	 
	public void addNewUser() {
		Dialog<User> dialog= new Dialog<>();
		dialog.setTitle("Új felhasználó hozzáadása");
		dialog.setContentText("Adja meg az új felhasználó adatait:");
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
		TextField nameText= new TextField();
		nameText.setPromptText("Teljes név");
		TextField unameText= new TextField();
		unameText.setPromptText("Felhasználónév");
		PasswordField passwdText= new PasswordField();
		passwdText.setPromptText("Kezdeti jelszó");
		CheckBox adminChckbox = new CheckBox();
		adminChckbox.setText("Admin felhasználó");
		dialogPane.setContent(new VBox(8,nameText,unameText,passwdText,adminChckbox));
		dialog.show();
		dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
            	byte adminByte=0;
            	if(adminChckbox.isSelected())
            		adminByte=1;
                String insertQuery="insert into users values('"+ unameText.getText()+"','"+ LoginController.passwordEncryption(passwdText.getText()) +"','"+ nameText.getText()+"',"+adminByte+")";
                try{
                	DatabaseConnection con=new DatabaseConnection();
					con.insertIntoDatabase(insertQuery);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            return null;
        });
	}
	
	public void changePassword() {
		Dialog<User> dialog= new Dialog<>();
		dialog.setTitle("Jelszó megváltoztatása");
		dialog.setContentText(user+" felhasználó jelszavának megváltoztatása");
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
		PasswordField oldPWText= new PasswordField();
		oldPWText.setPromptText("Régi jelszó");
		PasswordField newPWText= new PasswordField();
		newPWText.setPromptText("Új jelszó");
		PasswordField newPWText2= new PasswordField();
		newPWText2.setPromptText("Új jelszó mégegyszer");
		dialogPane.setContent(new VBox(8,oldPWText,newPWText,newPWText2));
		dialog.show();
		dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String updateQuery="update users set userpw='"+ LoginController.passwordEncryption(newPWText.getText()) +"' where username='"+user+"'";
                try{
                	DatabaseConnection con=new DatabaseConnection();
					ResultSet rs=con.doQuery("select userpw from users where username='"+user+"'");
					rs.next();
					if(oldPWText.getText().isBlank()||newPWText.getText().isBlank()||newPWText2.getText().isBlank()||!newPWText.getText().equals(newPWText2.getText()))
							{throw new Exception();}
					con.updateDatabase(updateQuery);
				} catch (Exception e) {
					Alert a=new Alert(AlertType.ERROR);
			        a.setHeaderText("Hibás régi jelszó, vagy az új jelszó nem egyezik!");
			        a.setTitle("Hiba!");
			        a.show();
					e.printStackTrace();
				}
            }
            return null;
        });
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usernameLabel.setText(user);
		userAdd.setVisible(isadmin);
	}
}
