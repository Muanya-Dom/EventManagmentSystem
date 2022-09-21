package app.Settings;

import app.Model.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SettingsController
{
    Database database = new Database();

	
    @FXML
    private void request(ActionEvent event)
    {
    	// checks if a student already has organization right
		boolean result = database.doesStudentHaveOrganisationRight();
		if (result == true)
		{
			// error message box
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Event Managemet System - UOB");
			alert.setContentText("You have already been granted organisation right.");
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		else 
		{
			// checks if a student has already sent request before
			boolean result1 = database.isRequestAlreadySent();
			if (result1 == true) 
			{
				// error message box
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Event Managemet System - UOB");
				alert.setContentText("You have already sent a request.");
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			else 
			{
				// send request to database
				database.requestForOrganisationRight();
				
				// confirmation message box
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Event Managemet System - UOB");
				alert.setContentText("Your request has successfully been sent.");
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			
		}
    }
	
    
    
    
	
	
}
