package application;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
/**
 * Bejelentkeztetést végrehajtó osztály
 * @author Hujber Ádám
 *
 */
public class LoginController implements Initializable {
	/**
	 * jelszó mezõ
	 */
	@FXML
	private PasswordField passwd;
	/**
	 * felhasználónév mezõ
	 */
	@FXML
	private TextField username;
	@FXML
	private AnchorPane loginAnchorPane;
	@FXML
	private Button loginButton;
	
	private SceneSwitcher sw = new SceneSwitcher();
	/**
	 * Beléptetést végzõ függvény. Sikeres azonosítás esetén megnyitja a fõmenüt.
	 * @param e
	 */
	public void loginAttempt(Event e){
		
		String user=username.getText();
		String pass=passwd.getText();
		String url = "jdbc:sqlserver://DESKTOP-NS687T6:1433; databaseName=GINTONIC_BANK; encrypt=true; trustServerCertificate=true; integratedSecurity=true;";
		try (Connection con = DriverManager.getConnection(url); Statement stmt = con.createStatement();){
			//System.out.println("Sikeres kapcsolodas!");
			String SQL = "SELECT  * FROM users";
	        ResultSet rs = stmt.executeQuery(SQL);

	        while(rs.next())
	        {
	        	if(rs.getString("username").equals(user) && rs.getString("userpw").equals(passwordEncryption(pass)))
	        	{
	        		if(rs.getByte("admin")==1)
	        		{
	        			sw.setUser(user,true);
	        			sw.switchToMenu(e);
	        			return;
	        		}
	        		else
	        		{
	        			sw.setUser(user,false);
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
	/**
	 * Enter leütéssel helyettesíthetõ a gombnyomás
	 * @param e
	 */
	public void enterButton(KeyEvent e) {
		if(e.getCode() == KeyCode.ENTER)
	        loginAttempt(e);
	}
	/**
	 * Jelszó titkosítást kezelõ függvény
	 * @param pass bemeneti jelszó normál formában
	 * @return visszaadja a titkosított jelszó stringet
	 */
	public static String passwordEncryption(String pass)
	{
        String encryptedpassword = null;  
        try   
        {  
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(pass.getBytes());   
            byte[] bytes = m.digest();  
            StringBuilder s = new StringBuilder();  
            for(int i=0; i< bytes.length ;i++)  
            {  
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
            }  
            encryptedpassword = s.toString();  
        }   
        catch (NoSuchAlgorithmException e)   
        {  
            e.printStackTrace();  
        }
        return encryptedpassword;
    }  
	
	
	/**
	 * Inicializáló függvény beállítja a hátteret
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Stop[] stops = new Stop[] {new Stop(0,Color.rgb(27, 198, 245)), new Stop(1,Color.rgb(85, 245, 27))};
		LinearGradient lgcolor = new LinearGradient(0, 1, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		
		BackgroundFill bgFill=new BackgroundFill(lgcolor,CornerRadii.EMPTY,Insets.EMPTY);
		loginAnchorPane.setBackground(new Background(bgFill));
		
		
	}
	
}
