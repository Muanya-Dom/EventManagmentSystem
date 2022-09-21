package app.StartGUI;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class StartGUI extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../Login/Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Event Managemet System - UOB");
			primaryStage.getIcons().add(new Image(StartGUI.class.getResourceAsStream("../Image/uob_icon.png")));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

		
			
			}
			
	

	public static void main(String[] args) {
		launch(args);
	}
}
