package app.Bookings;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import app.Model.Database;
import app.Model.SharedModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UpdateBookingsController implements Initializable
{

	@FXML
    private AnchorPane acpUpdateBookings;

    @FXML
    private TextField txtEventTitle;

    @FXML
    private TextField txtURL;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtTime;
    
    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtOrganisation;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtCampus;

    @FXML
    private TextField txtRoomNo;
    
    @FXML
    private ComboBox<Integer> cmbNoOfSpace;
    
    @FXML
    private Label lblErrorMessage;
    
    
    Database database = new Database();

    
    ObservableList<Integer> listOfNumbers = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10);
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		lblErrorMessage.setText("");
		cmbNoOfSpace.setItems(listOfNumbers);
		
		txtEventTitle.setText(SharedModel.TITLE);
    	txtDescription.setText(SharedModel.DESCIPTION);
    	txtLocation.setText(SharedModel.LOCATION);
    	txtOrganisation.setText(SharedModel.ORGANISATION);
    	txtURL.setText(SharedModel.URL);
		txtCampus.setText(SharedModel.CAMPUS);
		txtRoomNo.setText(SharedModel.ROOM_NO);
		txtTime.setText(SharedModel.TIME);
		txtDate.setText(SharedModel.DATE);
		cmbNoOfSpace.setValue(SharedModel.NO_OF_SPACE);
		
	}
	
	
    @FXML
    private void Complete(ActionEvent event) throws IOException 
    {
    	if (cmbNoOfSpace.getValue() == null) 
    	{
    		lblErrorMessage.setText("Select the number of space you would like.");
    		return;
		}
    	
		// -------------- all validations above has been fulfilled ------------
		
		// clear error message
		lblErrorMessage.setText("");
		
		// get current date
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String bookingDate = dateFormat.format(date);
		int bookingID = SharedModel.BOOKING_ID;
		int eventId = SharedModel.EVENT_ID;
		int newNoOfSpace = cmbNoOfSpace.getValue();	
		
		// update booking - database
		database.onUpdateBooking(bookingID, eventId, bookingDate, newNoOfSpace);
		
		// confirmation message box
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Event Managemet System - UOB");
		alert.setContentText("Booking updated successfully.");
		alert.setHeaderText(null);
		alert.showAndWait();
		
		// go to Bookings page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("Bookings.fxml"));
		acpUpdateBookings.getChildren().setAll(pane);
    }
    
    

}
		
		


