package app.ManageEvents;

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

public class ViewEventsController implements Initializable 
{
    @FXML
    private AnchorPane acpViewEvents;
    
	@FXML private TableView<Events> viewEventsTable;
	@FXML private TableColumn<Events, Integer> eventid;
	@FXML private TableColumn<Events, String> title;
	@FXML private TableColumn<Events, String> date;
	@FXML private TableColumn<Events, String> time;
	@FXML private TableColumn<Events, Integer> noOfSpaceAvailable;
	
	Database database = new Database();
			
	public ObservableList<Events> listOfEvents = FXCollections.observableArrayList();
			
			
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		listOfEvents.addAll(database.viewEventsTableData());
		
		initialseTableColumns();
		
		viewEventsTable.setItems(listOfEvents);
		
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
    private void UpdateEvent(ActionEvent event) throws IOException
    {
		if (!viewEventsTable.getSelectionModel().isEmpty()) 
		{
			int eventid = viewEventsTable.getSelectionModel().getSelectedItem().getEventid();
			
			// get selected event details from database
			database.getEventsDetails(eventid);
						
			// go to Update Event page
			AnchorPane pane = FXMLLoader.load(getClass().getResource("UpdateEvents.fxml"));
			acpViewEvents.getChildren().setAll(pane);
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
    private void DeleteEvent(ActionEvent event) throws IOException
    {
		if (!viewEventsTable.getSelectionModel().isEmpty()) 
		{
			int eventid = viewEventsTable.getSelectionModel().getSelectedItem().getEventid();
			
	    	database.onDeleteEvent(eventid);
	    	
			// refresh itself
			AnchorPane pane = FXMLLoader.load(getClass().getResource("ViewEvents.fxml"));
			acpViewEvents.getChildren().setAll(pane);
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
