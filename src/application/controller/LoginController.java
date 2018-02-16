package application.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.Main;
import application.database.DBConnector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {
	
    @FXML
    private PasswordField pf_pass;

    @FXML
    private TextField tf_username;

    @FXML
    private Button btn_login;
    
    @FXML
    private Button btn_register;

    @FXML
    void loginClicked(MouseEvent event) throws IOException {

    	String username = tf_username.getText();
    	
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/application/view/UserView.fxml"));

    	try {
    		loader.load();
    	} catch(IOException e){
    		e.printStackTrace();
    	}
    	
    	TableController tc = loader.getController();
    	tc.setUserLabel(username);
    	Parent parent = loader.getRoot();
    	Scene scene = new Scene(parent);
    	Main.getPrimaryStage().setScene(scene);
//    	Main.getPrimaryStage().showAndWait();
    	
    }

    @FXML
    void registerUser(MouseEvent event) {

    	DBConnector dbconnector = new DBConnector();
		Connection connection = null;

		try {
			connection = dbconnector.connection();
			String sql = "SELECT * FROM concerts WHERE username = ? and password = ?"; 
			PreparedStatement stmt = dbconnector.connection().prepareStatement(sql);
			stmt.setString(1, tf_username.getText());
			stmt.setString(2, pf_pass.getText());
			ResultSet rs = stmt.executeQuery();
			
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
    }
}
