package app.Events;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import app.Model.Database;
import app.Model.Events;
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

public class EventsController implements Initializable 
{
	@FXML
	private AnchorPane acpEvents;
	@FXML private TableView<Events> eventsTable;
	@FXML private TableColumn<Events, Integer> eventid;
	@FXML private TableColumn<Events, String> title;
	@FXML private TableColumn<Events, String> date;
	@FXML private TableColumn<Events, String> time;
	@FXML private TableColumn<Events, Integer> noOfSpaceAvailable;
	
	Database database = new Database();
	
	
//	public ObservableList<Events> list = FXCollections.observableArrayList(
//			new Events ("E01", "London tower", "Excursion", "07/04/20", "London", "NULL", "18:20" ,30),
//			new Events ("E02", "Eatry", "Food Competition", "08/04/20", "Luton", "NULL", "21:30", 25),
//			new Events ("E03", "Lecture", "Maths", "30/07/20", "NULL", "www.i.com", "9:30", 30)
//			);
			
	public ObservableList<Events> listOfEvents = FXCollections.observableArrayList();
			
			
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		listOfEvents.addAll(database.eventsTableData());
		
		initialseTableColumns();
		
		eventsTable.setItems(listOfEvents);
		
	}
	
	
	private void initialseTableColumns()
	{
		eventid.setCellValueFactory(new PropertyValueFactory<Events, Integer>("eventid"));
		title.setCellValueFactory(new PropertyValueFactory<Events, String>("title"));
		date.setCellValueFactory(new PropertyValueFactory<Events, String>("date"));			
		time.setCellValueFactory(new PropertyValueFactory<Events, String>("time"));	
		noOfSpaceAvailable.setCellValueFactory(new PropertyValueFactory<Events, Integer>("noOfSpaceAvailable"));	
	}
	
	
	
	@FXML
    private void bookEvent(ActionEvent event) throws IOException 
	{
		if (!eventsTable.getSelectionModel().isEmpty()) 
		{
			int eventid = eventsTable.getSelectionModel().getSelectedItem().getEventid();
			
			// get selected event details from database
			database.getEventsDetails(eventid);
						
			// go to Update Event page
			AnchorPane pane = FXMLLoader.load(getClass().getResource("EventDetails.fxml"));
			acpEvents.getChildren().setAll(pane);
	    }
		else 
		{
			// error message box
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Event Managemet System - UOB");
			alert.setContentText("Select from the table to book.");
			alert.setHeaderText(null);
			alert.showAndWait();
		}
    }
	
	
	
	

}
