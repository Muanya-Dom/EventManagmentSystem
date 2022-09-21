package app.Bookings;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import app.Model.Bookings;
import app.Model.Database;
import app.Model.SharedModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class BookingsController implements Initializable 
{
    @FXML
    private AnchorPane acpBookings;
	@FXML private TableView<Bookings> bookingTable;
	@FXML private TableColumn<Bookings, Integer> BookingID;
	@FXML private TableColumn<Bookings, Integer> EventID;
	@FXML private TableColumn<Bookings, String> Title;
	@FXML private TableColumn<Bookings, String> BookingDate;
	@FXML private TableColumn<Bookings, Integer> NoOfSpace;
	
	Database database = new Database();
	
	public ObservableList<Bookings> listOfBookings = FXCollections.observableArrayList();			
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		listOfBookings.addAll(database.bookedEventsTableData());

		initialseTableColumns();
		
		bookingTable.setItems(listOfBookings);
		
	}
	
	private void initialseTableColumns()
	{
		BookingID.setCellValueFactory(new PropertyValueFactory<Bookings, Integer>("BookingID"));
		EventID.setCellValueFactory(new PropertyValueFactory<Bookings, Integer>("EventID"));
		Title.setCellValueFactory(new PropertyValueFactory<Bookings, String>("Title"));
		BookingDate.setCellValueFactory(new PropertyValueFactory<Bookings, String>("BookingDate"));
		NoOfSpace.setCellValueFactory(new PropertyValueFactory<Bookings, Integer>("NoOfSpace"));
	}
	
	
	@FXML
    private void update(ActionEvent event) throws IOException
    {
		if (!bookingTable.getSelectionModel().isEmpty()) 
		{
			SharedModel.BOOKING_ID = bookingTable.getSelectionModel().getSelectedItem().getBookingID();
			SharedModel.NO_OF_SPACE = bookingTable.getSelectionModel().getSelectedItem().getNoOfSpace();
			int eventID = bookingTable.getSelectionModel().getSelectedItem().getEventID();
			
			// get selected event details from database
			database.getEventsDetails(eventID);
						
			// go to Update Event page
			AnchorPane pane = FXMLLoader.load(getClass().getResource("UpdateBookings.fxml"));
			acpBookings.getChildren().setAll(pane);
	    }
		else 
		{
			// error message box
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Event Managemet System - UOB");
			alert.setContentText("Select from the table to update.");
			alert.setHeaderText(null);
			alert.showAndWait();
		}
    }

	
	
	@FXML
    private void delete(ActionEvent event) throws IOException 
	{
		if (!bookingTable.getSelectionModel().isEmpty())
		{
			int bookingID = bookingTable.getSelectionModel().getSelectedItem().getBookingID();
			int eventId = bookingTable.getSelectionModel().getSelectedItem().getEventID();
			int noOfSpace = bookingTable.getSelectionModel().getSelectedItem().getNoOfSpace();
			
	    	database.onDeleteBooking(bookingID, eventId, noOfSpace);
	    	
			// refresh itself
			AnchorPane pane = FXMLLoader.load(getClass().getResource("Bookings.fxml"));
			acpBookings.getChildren().setAll(pane);
	    }
		else 
		{
			// error message box
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Event Managemet System - UOB");
			alert.setContentText("Select from the table to delete.");
			alert.setHeaderText(null);
			alert.showAndWait();
		}
    }

	
	
    
	

}
