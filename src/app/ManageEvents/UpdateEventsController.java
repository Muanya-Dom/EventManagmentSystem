package app.ManageEvents;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import app.Model.Database;
import app.Model.SharedModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class UpdateEventsController implements Initializable
{

	@FXML
    private AnchorPane acpUpdateEvents;

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
		
		txtEventTitle.setText(SharedModel.TITLE);
    	txtDescription.setText(SharedModel.DESCIPTION);
    	txtLocation.setText(SharedModel.LOCATION);
    	txtOrganisation.setText(SharedModel.ORGANISATION);
    	txtURL.setText(SharedModel.URL);
		txtCampus.setText(SharedModel.CAMPUS);
		txtRoomNo.setText(SharedModel.ROOM_NO);
		txtTime.setText(SharedModel.TIME);
		
		String noOfSpace = Integer.toString(SharedModel.TOTAL_NO_OF_SPACE);
		txtNoOfSpace.setText(noOfSpace);
		
		LocalDate date = LocalDate.parse(SharedModel.DATE);
		dtDate.setValue(date);
		
	}
	
	
    @FXML
    private void Update(ActionEvent event) throws IOException 
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
		int newTotalNoOfSpace = 0;
		try
	    {
			newTotalNoOfSpace = Integer.parseInt(noOfSpace);
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
		int eventid = SharedModel.EVENT_ID;
		
		// clear error message
		lblErrorMessage.setText("");
		
		// add details to database
		database.onUpdateEvent(eventid, title, description, location, organisation, url, campus, roomNo, time, date, newTotalNoOfSpace);
		
		// confirmation message box
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Event Managemet System - UOB");
		alert.setContentText("Event updated successfully.");
		alert.setHeaderText(null);
		alert.showAndWait();
		
		// go to View Events page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("ViewEvents.fxml"));
		acpUpdateEvents.getChildren().setAll(pane);
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
    private void Delete(ActionEvent event) throws IOException 
    {
		int eventid = SharedModel.EVENT_ID;
		
    	database.onDeleteEvent(eventid);
    	
    	// View Events page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("ViewEvents.fxml"));
		acpUpdateEvents.getChildren().setAll(pane);
    }
    


}
		
		


