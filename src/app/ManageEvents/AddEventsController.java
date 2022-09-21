package app.ManageEvents;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import app.Model.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddEventsController implements Initializable
{

	@FXML
    private AnchorPane acpAddEvents;

    @FXML
    private Button AddEvent;

    @FXML
    private TextField txtEventTitle;

    @FXML
    private TextField txtURL;

    @FXML
    private DatePicker dtDate;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtTime;

    @FXML
    private TextField txtNoOfSpace;

    @FXML
    private RadioButton rbtOnlineEvent;

    @FXML
    private RadioButton rbtExternalVisit;

    @FXML
    private RadioButton rbtInternalVisit;

    @FXML
    private TextField txtOrganisation;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtCampus;

    @FXML
    private TextField txtRoomNo;
    
    @FXML
    private Label lblErrorMessage;
    
    
    Database database = new Database();

    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		lblErrorMessage.setText("");
	}
	
	
    @FXML
    private void AddEvent(ActionEvent event) throws IOException 
    {
    	String title = txtEventTitle.getText();
    	String description = txtDescription.getText();
    	String location = txtLocation.getText();
    	String organisation = txtOrganisation.getText();
    	String url = txtURL.getText();
		String campus = txtCampus.getText();
		String roomNo = txtRoomNo.getText();
		String time = txtTime.getText();
		String noOfSpace = txtNoOfSpace.getText();
		
		 // if text boxes are empty
		if(title.isEmpty() || description.isEmpty() || time.isEmpty() || noOfSpace.isEmpty()) 
		{
			lblErrorMessage.setText("All fields are required");
			return; // kicks you out of the method
		}
		
		// date must not be empty
		String date;
		try 
		{
			date = dtDate.getValue().toString();
		} 
		catch (Exception e) {
			lblErrorMessage.setText("Date required");
			return; // kicks you out of the method
		}
		
		// no of space must be a number
		int totalNoOfSpace = 0;
		try
	    {
			totalNoOfSpace = Integer.parseInt(noOfSpace);
	    }
		catch (NumberFormatException ex) {
	        lblErrorMessage.setText("Number of space must be a number");
	        return; // kicks you out of the method
	    }
		
		// url, organization, location, campus and roomNo text box must not be empty
		if(url.isEmpty() && organisation.isEmpty() && location.isEmpty()
				&& campus.isEmpty() && roomNo.isEmpty()) 
		{
			lblErrorMessage.setText("Select either an Online, External or Internal Event");
	        return; // kicks you out of the method
		}
		
		
		// -------------- all validations above has been fulfilled ------------
		
		int noOfSpaceAvailable = totalNoOfSpace;
		int studentId = Database.STUDENTID;
		
		// clear error message
		lblErrorMessage.setText("");
		
		// add details to database
		database.onAddEvent(title, description, location, organisation, url, campus, roomNo, time, date, totalNoOfSpace, noOfSpaceAvailable, studentId);
		
		// confirmation message box
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Event Managemet System - UOB");
		alert.setContentText("Event added successfully.");
		alert.setHeaderText(null);
		alert.showAndWait();
		
		// go to View Events page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("ViewEvents.fxml"));
		acpAddEvents.getChildren().setAll(pane);
    }
    
    
    // ----- radio box validation ------
    @FXML
    private void radioButtonOnSelect(ActionEvent event)
    {
    	// online event radio button 
		if(rbtOnlineEvent.isSelected())
		{
			txtURL.setEditable(true);
			
			txtOrganisation.setText("");
			txtOrganisation.setEditable(false);
			
			txtLocation.setText("");
			txtLocation.setEditable(false);
			
			txtCampus.setText("");
			txtCampus.setEditable(false);
			
			txtRoomNo.setText("");
			txtRoomNo.setEditable(false);
		}
		
		// external event radio button 
		if(rbtExternalVisit.isSelected())
		{
			txtOrganisation.setEditable(true);
			txtLocation.setEditable(true);
			
			txtURL.setText("");
			txtURL.setEditable(false);
			
			txtCampus.setText("");
			txtCampus.setEditable(false);
			
			txtRoomNo.setText("");
			txtRoomNo.setEditable(false);
		}
		
		// external event radio button 
		if(rbtInternalVisit.isSelected())
		{
			txtCampus.setEditable(true);
			txtRoomNo.setEditable(true);
			
			txtURL.setText("");
			txtURL.setEditable(false);
			
			txtOrganisation.setText("");
			txtOrganisation.setEditable(false);
			
			txtLocation.setText("");
			txtLocation.setEditable(false);
		}
		
    }


    
    @FXML
    private void ViewEvents(ActionEvent event) throws IOException 
    {
    	// View Events page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("ViewEvents.fxml"));
		acpAddEvents.getChildren().setAll(pane);
    }
    


}
		
		


