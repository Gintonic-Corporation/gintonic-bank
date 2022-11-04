package application;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;

public class LoginController implements Initializable {
	@FXML
	private PasswordField passwd;
	@FXML
	private TextField username;
	@FXML
	private AnchorPane loginAnchorPane;
	
	private SceneSwitcher sw = new SceneSwitcher();
	public void loginAttempt(ActionEvent e){
		
		String user=username.getText();
		String pass=passwd.getText();
		String url = "jdbc:sqlserver://DESKTOP-NS687T6:1433; databaseName=GINTONIC_BANK; encrypt=true; trustServerCertificate=true; integratedSecurity=true;";
		try (Connection con = DriverManager.getConnection(url); Statement stmt = con.createStatement();){
			System.out.println("Sikeres kapcsolodas!");
			String SQL = "SELECT  * FROM users";
	        ResultSet rs = stmt.executeQuery(SQL);

	        while(rs.next())
	        {
	        	if(rs.getString("username").equals(user) && rs.getString("userpw").equals(pass))
	        	{
	        		sw.setUser(user);
	        		if(rs.getByte("admin")==1)
	        		{
	        			System.out.println("Sikeres bejelentkezés.");
	        			sw.switchToMenu(e);
	        			return;
	        		}
	        		else
	        		{
	        			System.out.println("Sikeres bejelentkezés.");
	        			sw.switchToMenu(e);
	        			return;
	        		}
	        	}
	        }
	        Alert a=new Alert(AlertType.ERROR);
	        a.setHeaderText("Hibás felhasználónév vagy jelszó!");
	        a.setTitle("Hiba!");
	        a.show();
	        System.out.println("Sikertelen bejelentkezés");
		}
		catch(SQLException ex) {
			System.out.println("HIBA!");
			ex.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Stop[] stops = new Stop[] {new Stop(0,Color.rgb(27, 198, 245)), new Stop(1,Color.rgb(85, 245, 27))};
		LinearGradient lgcolor = new LinearGradient(0, 1, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		
		BackgroundFill bgFill=new BackgroundFill(lgcolor,CornerRadii.EMPTY,Insets.EMPTY);
		loginAnchorPane.setBackground(new Background(bgFill));
		
	}
	
	
}
