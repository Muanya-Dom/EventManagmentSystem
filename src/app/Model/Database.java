package app.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class Database 
{
	private final String HOST = "localhost:3308";
	private final String DB_NAME = "uob_event_management_db";
	private final String DisableSSL = "?autoReconnect=true&useSSL=false";
	private final String DB_URL = "jdbc:mysql://" + HOST + "/" + DB_NAME + DisableSSL;
	
	private final String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	
	public Connection connection;
	public Statement statement;
	private ResultSet resultSet;

	
	// distributed variables
	public static int STUDENTID;
	public static String FIRST_NAME = null;
	public static String LAST_NAME = null;
	public static String ORGANISATION_RIGHT = null;
	
	public Database() {
		DBConnection();
	}
	
	
	public void DBConnection()
	{
		try 
		{
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			statement = connection.createStatement();
			
			// If the above code is established without errors
			System.out.println("Connection Accepted.");
		} 
		catch (Exception ex)
		{
			System.out.println("Connection Denied!!");
			ex.printStackTrace();
			System.out.println(ex.toString());
		}
		
	}
	

	
	
	//<--------------------------- System DB Functions --------------------------------->
	
	// Check if a student ID is in database
	public boolean doesStudentIDExistInDataBase(String studentID) 
	{
		boolean result = false;
		
		try {
			String query = String.format("SELECT studentID FROM student where StudentID = '%s'", studentID);
			
			resultSet = statement.executeQuery(query);
			
			if(resultSet.next()) 
			{
				result = true;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		// returns result
		return result;
	}
	
	
	
	// Check if a student has organization right
	public boolean doesStudentHaveOrganisationRight() 
	{
		boolean result = false;
		
		try {
			String query = String.format("SELECT * FROM student where StudentID = '%s' AND OrganisationRight = 'Granted'", Database.STUDENTID);
			
			resultSet = statement.executeQuery(query);
			
			if(resultSet.next())
			{
				result = true;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	// Check if a student has already sent organization request
	public boolean isRequestAlreadySent() 
	{
		boolean result = false;
		
		try {
			String query = String.format("SELECT * FROM request where StudentID = '%s'", Database.STUDENTID);
			
			resultSet = statement.executeQuery(query);
			
			if(resultSet.next()) 
			{
				result = true;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		// returns result
		return result;
	}
		
	
	
	// Login
	public boolean onLoginSuccess(String studentID, String password) 
	{
		// default result
		boolean result = false;
		
		try {
			String query = String.format("SELECT * FROM student WHERE StudentID = '%s' AND Pa55w0rd = '%s'", studentID, password);
			
			resultSet = statement.executeQuery(query);
			
			if(resultSet.next()) 
			{
				result = true;
				STUDENTID = resultSet.getInt("StudentID");
				FIRST_NAME = resultSet.getString("FirstName");
				LAST_NAME = resultSet.getString("LastName");
				ORGANISATION_RIGHT = resultSet.getString("OrganisationRight");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		// returns result
		return result;
	}
	
	

	// Password Update for Forget Password Page
	public void PasswordUpdate(String studentID, String newPassword) 
	{		
		try {
			String query = String.format("UPDATE student SET Pa55w0rd = '%s' WHERE StudentID = '%s'", newPassword, studentID);
			statement.execute(query);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	
	
	// Register new user
	public void onRegisterSuccess(int studentId, String firstName, String lastName, String password) 
	{		
		try 
		{
			String query = String.format("INSERT INTO student(StudentID, FirstName, LastName, OrganisationRight, Pa55w0rd) "
					+ "VALUES (%s, '%s', '%s', '%s', '%s')", studentId, firstName, lastName, null, password);
			
			statement.execute(query);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
		
	
	
	// Events Table (shows all event in database)
	public ArrayList<Events> eventsTableData()
	{
		ArrayList<Events> listOfData = new ArrayList<Events>();
		
		int eventid;
		String title;
		String date;
		String time;
		int noOfSpaceAvailable;
		
		try
		{
			String query = String.format("SELECT * FROM event");
			
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) 
			{
				eventid = resultSet.getInt("EventID");
				title = resultSet.getString("Title");
				date = resultSet.getString("Date");
				time = resultSet.getString("Time");
				noOfSpaceAvailable = resultSet.getInt("NoOfSpaceAvailable");
				
				listOfData.add(new Events(eventid,title, date, time,noOfSpaceAvailable));
			}
		} 
		catch (Exception e)  
		{
			e.printStackTrace();
		}
		
		return listOfData;
	}
	
	
	
	// Add Event
	public void onAddEvent(String title, String description, String location, String organisation, String url,
			String campus, String roomNo, String time, String date, int totalNoOfSpace, int noOfSpaceAvailable, int studentId)
	{
		try 
		{
			String query = String.format("INSERT INTO event(Title, Description, Location, Organisation, URL, Campus, RoomNo, Time, Date, TotalNoOfSpace, NoOfSpaceAvailable, StudentID)"
										+ " VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
										title, description, location, organisation, url, campus, roomNo, time, date, totalNoOfSpace, noOfSpaceAvailable, studentId);
		
			statement.execute(query);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	
	
	// View Events Table (shows all event in database for a particular event organiser)
	public ArrayList<Events> viewEventsTableData()
	{
		ArrayList<Events> listOfData = new ArrayList<Events>();
		
		int eventid;
		String title;
		String date;
		String time;
		int noOfSpaceAvailable;
		
		try
		{
			String query = String.format("SELECT * FROM event WHERE StudentID = '%s'", Database.STUDENTID);
			
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) 
			{
				eventid = resultSet.getInt("EventID");
				title = resultSet.getString("Title");
				date = resultSet.getString("Date");
				time = resultSet.getString("Time");
				noOfSpaceAvailable = resultSet.getInt("NoOfSpaceAvailable");
				
				listOfData.add(new Events(eventid,title, date, time,noOfSpaceAvailable));
			}
		} 
		catch (Exception e)  
		{
			e.printStackTrace();
		}
		
		return listOfData;
	}
	
	
	
	// Get Event ID Details (get all details for an event id from the database)
	public void getEventsDetails(int eventid)
	{
		try
		{
			String query = String.format("SELECT * FROM event WHERE EventID = '%s'", eventid);
			
			resultSet = statement.executeQuery(query);
			
			if(resultSet.next())
			{
				SharedModel.EVENT_ID = resultSet.getInt("EventID");
				SharedModel.TITLE = resultSet.getString("Title");
				SharedModel.DESCIPTION = resultSet.getString("Description");
				SharedModel.LOCATION = resultSet.getString("Location");
				SharedModel.ORGANISATION = resultSet.getString("Organisation");
				SharedModel.URL = resultSet.getString("URL");
				SharedModel.CAMPUS = resultSet.getString("Campus");
				SharedModel.ROOM_NO = resultSet.getString("RoomNo");
				SharedModel.TIME = resultSet.getString("Time");
				SharedModel.DATE = resultSet.getString("Date");
				SharedModel.TOTAL_NO_OF_SPACE = resultSet.getInt("TotalNoOfSpace");
				SharedModel.NO_OF_SPACE_AVAILABLE = resultSet.getInt("NoOfSpaceAvailable");
			}
		} 
		catch (Exception e)  
		{
			e.printStackTrace();
		}
	}

	
	
	// Get Number of Space Available for an Event IDs
	public void getNoOfSpaceAvailable(int eventid)
	{
		try
		{
			String query = String.format("SELECT * FROM event WHERE EventID = '%s'", eventid);
			
			resultSet = statement.executeQuery(query);
			
			if(resultSet.next())
			{
				SharedModel.NO_OF_SPACE_AVAILABLE = resultSet.getInt("NoOfSpaceAvailable");
			}
		} 
		catch (Exception e)  
		{
			e.printStackTrace();
		}
	}
	
	
	
	// Update Event
	public void onUpdateEvent(int eventid, String title, String description, String location, String organisation, String url,
			String campus, String roomNo, String time, String date, int newTotalNoOfSpace)
	{
		try 
		{
			// space control
			int difference = newTotalNoOfSpace - SharedModel.TOTAL_NO_OF_SPACE;
			int noOfSpaceAvailable = difference + SharedModel.NO_OF_SPACE_AVAILABLE;
			if (noOfSpaceAvailable <= 0)
			{
				noOfSpaceAvailable = 0;
			}
			
			String query = String.format("UPDATE event SET Title='%s', Description='%s', Location='%s',"
					+ "Organisation='%s', URL='%s', Campus='%s', RoomNo='%s', Time='%s', Date='%s',"
					+ "TotalNoOfSpace='%s', NoOfSpaceAvailable='%s' WHERE EventID='%s'",
					title, description, location, organisation, url, campus, roomNo, time, date, newTotalNoOfSpace, noOfSpaceAvailable, eventid);
		
			statement.execute(query);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	
	
	// Delete Event
	public void onDeleteEvent(int eventid) 
	{
		try 
		{
			String query = String.format("DELETE FROM event WHERE EventID = '%s'", eventid);
			String query2 = String.format("DELETE FROM booking WHERE EventID = '%s'", eventid);
			
			statement.execute(query);
			statement.execute(query2);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	
	// Book Event
	public void onBookEvent(int eventId, int noOfSpace, String bookingDate, int studentId)
	{
		int noOfSpaceAvailable = SharedModel.NO_OF_SPACE_AVAILABLE - noOfSpace;
		
		try
		{
			String query = String.format("INSERT INTO booking(EventID, NoOfSpace, BookingDate, StudentID)"
					+ "VALUES ('%s', '%s', '%s', '%s')", eventId, noOfSpace, bookingDate, studentId);
			
			String query2 = String.format("UPDATE event SET NoOfSpaceAvailable = '%s' WHERE EventID = '%s'",
					noOfSpaceAvailable, eventId);
			
			statement.execute(query);
			statement.execute(query2);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	
	// Booked Events Table (shows all booked events in database for a particular student)
	public ArrayList<Bookings> bookedEventsTableData()
	{
		ArrayList<Bookings> listOfData = new ArrayList<Bookings>();
		
		Integer bookingID;
		Integer eventID;
		String title;
		String bookingDate;
		Integer noOfSpace;
		
		try
		{
			String query = String.format("SELECT BookingID, booking.EventID, Title, BookingDate, NoOfSpace " + 
					"FROM booking " + 
					"INNER JOIN event ON booking.EventID = event.EventID " + 
					"WHERE booking.StudentID = '%s';", Database.STUDENTID);
			
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) 
			{
				bookingID = resultSet.getInt("BookingID");
				eventID = resultSet.getInt("EventID");
				title = resultSet.getString("Title");
				bookingDate = resultSet.getString("BookingDate");
				noOfSpace = resultSet.getInt("NoOfSpace");
				
				listOfData.add(new Bookings(bookingID,eventID, title, bookingDate,noOfSpace));
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return listOfData;
	}

	
	
	// Delete Booking
	public void onDeleteBooking(int bookingId, int eventId, int noOfSpace) 
	{
		getNoOfSpaceAvailable(eventId);

		// update number of space available 
		int noOfSpaceAvailable = SharedModel.NO_OF_SPACE_AVAILABLE + noOfSpace;
				
		try 
		{
			String query = String.format("DELETE FROM booking WHERE BookingID = '%s'", bookingId);
			
			String query2 = String.format("UPDATE event SET NoOfSpaceAvailable = '%s' WHERE EventID = '%s'",
					noOfSpaceAvailable, eventId);
			
			statement.execute(query);
			statement.execute(query2);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	
	// Update Booking
	public void onUpdateBooking(int bookingID, int eventId, String bookingDate, int newNoOfSpace)
	{
		updateNoOfAvailableSpaceOnUpdateBooking(eventId, newNoOfSpace);
		
		try 
		{
			String query = String.format("UPDATE booking SET BookingDate='%s', NoOfSpace='%s' WHERE BookingID='%s'",
					bookingDate, newNoOfSpace, bookingID);
		
			statement.execute(query);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	
	
	// Update number of space available on update booking
	public void updateNoOfAvailableSpaceOnUpdateBooking(int eventId, int newNoOfSpace) 
	{
		getNoOfSpaceAvailable(eventId);
		
		// if the new number of space is greater than the previous number when updating booking
		// then update the event number of space available by subtracting from it
		if(newNoOfSpace > SharedModel.NO_OF_SPACE) 
		{
			int difference = newNoOfSpace - SharedModel.NO_OF_SPACE;
			int noOfSpaceAvailable = SharedModel.NO_OF_SPACE_AVAILABLE - difference;
			
			try 
			{
				String query = String.format("UPDATE event SET NoOfSpaceAvailable = '%s' WHERE EventID = '%s'",
						noOfSpaceAvailable, eventId);
				
				statement.execute(query);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		// if the new number of space is lesser than the previous number when updating booking
		// then update the event number of space available by adding to it
		if(newNoOfSpace < SharedModel.NO_OF_SPACE) 
		{
			int difference = SharedModel.NO_OF_SPACE - newNoOfSpace;
			int noOfSpaceAvailable = SharedModel.NO_OF_SPACE_AVAILABLE + difference;
			
			try 
			{
				String query = String.format("UPDATE event SET NoOfSpaceAvailable = '%s' WHERE EventID = '%s'",
						noOfSpaceAvailable, eventId);
				
				statement.execute(query);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	// Request for Organization Right
	public void requestForOrganisationRight () 
	{
		try 
		{
			String query = String.format("INSERT INTO request (StudentID) VALUES (%s)", Database.STUDENTID);
			
			statement.execute(query);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	
	
}
