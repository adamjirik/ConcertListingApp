package application.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.Main;
import application.database.DBConnector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	void loginClicked(MouseEvent event) throws IOException, SQLException {


		DBConnector dbconnector = new DBConnector();
		Connection connection = null;

		try {
			connection = dbconnector.connection();
			String sql = "SELECT * FROM users WHERE username = ? and password = ?"; 
			PreparedStatement stmt = dbconnector.connection().prepareStatement(sql);
			stmt.setString(1, tf_username.getText());
			stmt.setString(2, pf_pass.getText());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {


				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/application/view/UserView.fxml"));

				try {
					loader.load();
				} catch(IOException e){
					e.printStackTrace();
				} 

				TableController tc = loader.getController();
				tc.setUserLabel(tf_username.getText());
				Parent parent = loader.getRoot();
				Scene scene = new Scene(parent);
				Main.getPrimaryStage().setScene(scene);

			} else {
				System.out.println("user not found");
			}


		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(connection != null) {
				connection.close();
			}
		}

	}

	@FXML
	void registerUser(MouseEvent event) throws SQLException {
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Registration");
    	alert.setHeaderText(null);
		DBConnector dbconnector = new DBConnector();
		Connection connection = null;

		try {
			connection = dbconnector.connection();
			String sql = "SELECT * FROM users WHERE username = ?"; 
			PreparedStatement stmt = dbconnector.connection().prepareStatement(sql);
			stmt.setString(1, tf_username.getText());
			ResultSet rs = stmt.executeQuery();
			if(!rs.next()) {
				System.out.println("no user");
				String insert = "INSERT INTO users (username, password, role) VALUES (?, ?, \"user\")";
				stmt = dbconnector.connection().prepareStatement(insert);
				stmt.setString(1, tf_username.getText());
				stmt.setString(2, pf_pass.getText());
				stmt.executeUpdate();
				
				
		    	alert.setContentText("User registered!");
			} else {
				alert.setContentText("user exists!");
			}
			alert.showAndWait();

		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection != null) {
				connection.close();
			}
		}
	}
}
