package app.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Bookings 
{
	private final SimpleIntegerProperty BookingID;
	private final SimpleIntegerProperty EventID;
	private final SimpleStringProperty Title;
	private final SimpleStringProperty BookingDate;
	private final SimpleIntegerProperty NoOfSpace;
	
	public Bookings(Integer bookingID, Integer eventID, String title, String bookingDate, Integer noOfSpace) 
	{
		super();
		this.BookingID = new SimpleIntegerProperty(bookingID);
		this.EventID = new SimpleIntegerProperty(eventID);
		this.Title = new SimpleStringProperty(title);
		this.BookingDate = new SimpleStringProperty(bookingDate);
		this.NoOfSpace = new SimpleIntegerProperty(noOfSpace);
	}
	
	
	public Integer getBookingID()
	{
		return BookingID.get();
	}
	
	public Integer getEventID() 
	{
		return EventID.get();
	}

	public String getTitle()
	{
		return Title.get();
	}
	
	public String getBookingDate()
	{
		return BookingDate.get();
	}
	
	public Integer getNoOfSpace() 
	{
		return NoOfSpace.get();
	}

}
