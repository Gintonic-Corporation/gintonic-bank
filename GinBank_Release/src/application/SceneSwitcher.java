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
/**
 * K�l�nb�z� men�pontok k�z�tti v�lt�st megval�s�t� oszt�ly
 * @author Hujber �d�m
 *
 */
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
	 /**
	  * Login ablakb�l �tv�lt a f�men�re.
	  * @param event
	  */
	 @FXML
	 public void switchToMenu(Event event) {
	  try {
		root = FXMLLoader.load(getClass().getResource("home.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Kezd�lap");
		stage.show();
		stage.centerOnScreen();
		
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		  e.printStackTrace();
	  }
	 }
	 /**
	  * be�ll�tja a bejelentkezett szem�ly felhaszn�l�nev�t �s jogosults�g�t
	  * @param u felhaszn�l�n�v
	  * @param a admin jogosults�g�-e
	  */
	 public void setUser(String u,boolean a) {
		 user=u;
		 isadmin=a;
	 }
	 /**
	  * Megfelel� gomb aktiv�l�sa eset�n megnyitja az �j �gyf�l hozz�ad�s�t lehet�v� tev� ablakot
	  */
	 public void switchToNewClient() {
		  try {
			  Parent root1 = FXMLLoader.load(getClass().getResource("NewClient.fxml"));
			  Scene scene1 = new Scene(root1);
			  Stage newStage=new Stage();
			  newStage.setScene(scene1);
			  newStage.getIcons().add(new Image(SceneSwitcher.class.getClassLoader().getResourceAsStream("GintonicCorpLogo.png")));
			  newStage.setTitle("�j �gyf�l");
			  newStage.show();
			  newStage.centerOnScreen();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	}
	 /**
	  * Megfelel� gomb aktiv�l�sa eset�n megnyitja az adatkezel� ablakot
	  */
	 public void switchToQuery() {
		  try {
			  Parent root1 = FXMLLoader.load(getClass().getResource("Query.fxml"));
			  Scene scene1 = new Scene(root1);
			  Stage newStage=new Stage();
			  newStage.setScene(scene1);
			  newStage.getIcons().add(new Image(SceneSwitcher.class.getClassLoader().getResourceAsStream("GintonicCorpLogo.png")));
			  newStage.setTitle("�gyf�l adatok");
			  newStage.show();
			  newStage.centerOnScreen();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	}
	 /**
	  * Megfelel� gomb aktiv�l�sa eset�n megnyitja az �j sz�mla l�trehoz�s�t lehet�v� tev� ablakot
	  */
	 public void switchToNewAccount() {
		  try {
			  Parent root1 = FXMLLoader.load(getClass().getResource("NewAccount.fxml"));
			  Scene scene1 = new Scene(root1);
			  Stage newStage=new Stage();
			  newStage.setScene(scene1);
			  newStage.getIcons().add(new Image(SceneSwitcher.class.getClassLoader().getResourceAsStream("GintonicCorpLogo.png")));
			  newStage.setTitle("Sz�mla nyit�s/z�r�s");
			  newStage.show();
			  newStage.centerOnScreen();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	}
	 /**
	  * Megfelel� gomb aktiv�l�sa eset�n megnyitja az utal�s kezdem�nyez�s�t lehet�v� tev� ablakot
	  */
	 public void switchToTransaction() {
		  try {
			  Parent root1 = FXMLLoader.load(getClass().getResource("Transaction.fxml"));
			  Scene scene1 = new Scene(root1);
			  Stage newStage=new Stage();
			  newStage.setScene(scene1);
			  newStage.getIcons().add(new Image(SceneSwitcher.class.getClassLoader().getResourceAsStream("GintonicCorpLogo.png")));
			  newStage.setTitle("�tutal�s");
			  newStage.show();
			  newStage.centerOnScreen();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	}
	 /**
	  * Megfelel� gomb aktiv�l�sa eset�n megnyitja a sz�mlat�rt�net ablakot
	  */
	 public void switchToTransactionLog() {
		  try {
			  Parent root1 = FXMLLoader.load(getClass().getResource("TransactionLog.fxml"));
			  Scene scene1 = new Scene(root1);
			  Stage newStage=new Stage();
			  newStage.setScene(scene1);
			  newStage.getIcons().add(new Image(SceneSwitcher.class.getClassLoader().getResourceAsStream("GintonicCorpLogo.png")));
			  newStage.setTitle("Sz�mlat�rt�net");
			  newStage.show();
			  newStage.centerOnScreen();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	}
	 /**
	  * Megfelel� gomb aktiv�l�sa eset�n megnyitja a befizet�st/kifizet�st kezel� ablakot
	  */
	 public void switchToHandleCash() {
		  try {
			  Parent root1 = FXMLLoader.load(getClass().getResource("HandleCash.fxml"));
			  Scene scene1 = new Scene(root1);
			  Stage newStage=new Stage();
			  newStage.setScene(scene1);
			  newStage.getIcons().add(new Image(SceneSwitcher.class.getClassLoader().getResourceAsStream("GintonicCorpLogo.png")));
			  newStage.setTitle("Befizet�s/Kifizet�s");
			  newStage.show();
			  newStage.centerOnScreen();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	}
	 /**
	  * Men�ben l�v� felhaszn�l� hozz�ad�sa gomb megnyom�s�ra �j felhaszn�l� r�gz�t�s�t teszi lehet�v�. Ehhez k�l�n ablakba bek�ri az adatokat.
	  */
	public void addNewUser() {
		Dialog<User> dialog= new Dialog<>();
		dialog.setTitle("�j felhaszn�l� hozz�ad�sa");
		dialog.setContentText("Adja meg az �j felhaszn�l� adatait:");
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
		TextField nameText= new TextField();
		nameText.setPromptText("Teljes n�v");
		TextField unameText= new TextField();
		unameText.setPromptText("Felhaszn�l�n�v");
		PasswordField passwdText= new PasswordField();
		passwdText.setPromptText("Kezdeti jelsz�");
		CheckBox adminChckbox = new CheckBox();
		adminChckbox.setText("Admin felhaszn�l�");
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
	/**
	 * Jelsz� megv�ltoztat�s�t teszi lehet�v�. Ehhez bek�ri a r�gi jelsz�t, majd az �j jelsz�t k�tszer. Ezt k�vet�en ellen�rzi a helyess�g�t �s m�dos�tja
	 */
	public void changePassword() {
		Dialog<User> dialog= new Dialog<>();
		dialog.setTitle("Jelsz� megv�ltoztat�sa");
		dialog.setContentText(user+" felhaszn�l� jelszav�nak megv�ltoztat�sa");
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
		PasswordField oldPWText= new PasswordField();
		oldPWText.setPromptText("R�gi jelsz�");
		PasswordField newPWText= new PasswordField();
		newPWText.setPromptText("�j jelsz�");
		PasswordField newPWText2= new PasswordField();
		newPWText2.setPromptText("�j jelsz� m�gegyszer");
		dialogPane.setContent(new VBox(8,oldPWText,newPWText,newPWText2));
		dialog.show();
		dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String updateQuery="update users set userpw='"+ LoginController.passwordEncryption(newPWText.getText()) +"' where username='"+user+"'";
                try{
                	DatabaseConnection con=new DatabaseConnection();
					ResultSet rs=con.doQuery("select userpw from users where username='"+user+"'");
					rs.next();
					if(oldPWText.getText().isBlank()||newPWText.getText().isBlank()||newPWText2.getText().isBlank()||!newPWText.getText().equals(newPWText2.getText())||oldPWText.getText().equals(rs.getString(1)))
							{throw new Exception();}
					con.updateDatabase(updateQuery);
					Alert a=new Alert(Alert.AlertType.CONFIRMATION);
					a.setHeaderText("Sikeres jelsz�v�ltoztat�s!");
					a.setContentText("A felhaszn�l�i jelsz� sikeresen m�dosult.");
					a.show();
				} catch (Exception e) {
					Alert a=new Alert(AlertType.ERROR);
			        a.setHeaderText("Hib�s r�gi jelsz�, vagy az �j jelsz� nem egyezik!");
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
