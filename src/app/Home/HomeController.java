package app.Home;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import app.Model.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


public class HomeController implements Initializable
{
    @FXML
    private AnchorPane acpHome;

    @FXML
    private AnchorPane acpHomeBody;

    @FXML
    private Text lblWelcome;
    
    @FXML
    private Button btnManageEvents;
    
    
    Database database = new Database();
	

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// welcomes the user
		lblWelcome.setText("Welcome " + Database.FIRST_NAME + " " + Database.LAST_NAME);
		
		
		// disable manage events button if student does not have organization right
		boolean result = database.doesStudentHaveOrganisationRight();
		if (result == false)
		{
			btnManageEvents.setDisable(true);
		} 
		
		// opens events page
		try
		{
			AnchorPane pane = FXMLLoader.load(getClass().getResource("../Events/Events.fxml"));
			acpHomeBody.getChildren().setAll(pane);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	
    @FXML
    private void logout(ActionEvent event) throws IOException 
    {
    	// logs out and opens login page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../Login/Login.fxml"));
		acpHome.getChildren().setAll(pane);
    }
	
    @FXML
    private void profile(ActionEvent event) throws IOException 
    {
    	// profile page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../Profile/Profile.fxml"));
		acpHomeBody.getChildren().setAll(pane);
    }

    @FXML
    private void bookings(ActionEvent event) throws IOException 
    {
    	// Bookings page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../Bookings/Bookings.fxml"));
		acpHomeBody.getChildren().setAll(pane);
    }

    
    @FXML
    private void settings(ActionEvent event) throws IOException 
    {
    	// Settings page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../Settings/Settings.fxml"));
		acpHomeBody.getChildren().setAll(pane);
    }
    
    
    @FXML
    private void manageevents(ActionEvent event) throws IOException 
    {
    	// Events Management page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../ManageEvents/AddEvents.fxml"));
		acpHomeBody.getChildren().setAll(pane);
    }
    
    
    @FXML
    private void events(ActionEvent event) throws IOException 
    {
    	//  Events page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../Events/Events.fxml"));
		acpHomeBody.getChildren().setAll(pane);
    }
    
    
    
    
    
    
}
