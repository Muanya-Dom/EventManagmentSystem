package app.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Events 
{
	private final SimpleIntegerProperty eventid;
	private final SimpleStringProperty title;
	private final SimpleStringProperty date;
	private final SimpleStringProperty time;
	private final SimpleIntegerProperty noOfSpaceAvailable;



public Events (int eventid, String title, String date, String time, Integer noOfSpaceAvailable)
{
	this.eventid = new SimpleIntegerProperty(eventid);
	this.title = new SimpleStringProperty(title);
	this.date = new SimpleStringProperty(date);
	this.time = new SimpleStringProperty(time);
	this.noOfSpaceAvailable = new SimpleIntegerProperty(noOfSpaceAvailable);
	
}
	public Integer getEventid() {
		return eventid.get();
	}


	public String getTitle() {
		return title.get();
	}

	public String getDate() {
		return date.get();
	}

	
	public String getTime() {
		return time.get();
	}


	public Integer getNoOfSpaceAvailable() {
		return noOfSpaceAvailable.get();
	}
}


