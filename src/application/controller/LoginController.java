package application.controller;

import java.io.IOException;

import application.Main;
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

    	FXMLLoader loader = new FXMLLoader();
    	String username = tf_username.getText();
    	System.out.println(username);
    	Parent parent = FXMLLoader.load(getClass().getResource("/application/view/UserView.fxml"));
    	TableController tc = loader.getController();
    	tc.setArtist(username);
    	Scene scene = new Scene(parent);
    	Main.getPrimaryStage().setScene(scene);
//    	Main.getPrimaryStage().showAndWait();
    	
    }

    @FXML
    void registerUser(MouseEvent event) {

    }
}
