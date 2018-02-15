package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
private static Stage primaryStage;
	
	private void setPrimaryStage(Stage stage) {
		Main.primaryStage = stage;
	}
	
	public static Stage getPrimaryStage() {
		return Main.primaryStage;
	}
	
	@Override
	public void start(Stage primaryStage) {

		try {
			
			setPrimaryStage(primaryStage);
			Parent parent = FXMLLoader.load(getClass().getResource("/application/view/UserView.fxml"));
			Scene scene = new Scene(parent);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Application");
			primaryStage.show();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
