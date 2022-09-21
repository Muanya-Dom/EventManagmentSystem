package app.Register;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import app.Model.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;


public class RegisterController implements Initializable
{	
    @FXML
    private AnchorPane acpRegister;

	@FXML
	private Label lblErrorMessage;

	@FXML
	private TextField txtFirstName;
	
	@FXML
	private TextField txtLastName;
	
	@FXML
	private TextField txtStudentID;
	
    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private PasswordField txtConfirmPassword;
    
    Database database = new Database();
	

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		lblErrorMessage.setText("");
	}
	
	
	@FXML
	private void register(ActionEvent event) throws Exception
	{
		 String studentID = txtStudentID.getText();
		 String firstname = txtFirstName.getText();
	     String lastname = txtLastName.getText();
	     String newPassword = txtNewPassword.getText();
	     String confirmPassword = txtConfirmPassword.getText();
	     
	     // if text boxes are empty
		if(studentID.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || 
				newPassword.isEmpty() || confirmPassword.isEmpty()) 
		{
			lblErrorMessage.setText("All fields are required");
			return; // kicks you out of the method
		}
	     
		// if student id does not exist in the database
		if (database.doesStudentIDExistInDataBase(studentID) == true)
		{
			lblErrorMessage.setText("Student ID already exist");
			return;
		}
		
		// if both password do not match
		if (!newPassword.equals(confirmPassword)) 
		{
			lblErrorMessage.setText("Password does not match");
			return; // kicks you out of the method
		}
		
		int studentId = 0;
		// if student id is a number
		try
	    {
			studentId = Integer.parseInt(studentID);
	    } catch (NumberFormatException ex)
	    {
	        lblErrorMessage.setText("Student ID must be a number");
	        return; // kicks you out of the method
	    }
		

		// if the above condition has been met
		// register new student
		database.onRegisterSuccess(studentId, firstname, lastname, newPassword);
		
		// confirmation message box
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Event Managemet System - UOB");
		alert.setContentText("Registration Successful.");
		alert.setHeaderText(null);
		alert.showAndWait();
		
		// opens login page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../Login/Login.fxml"));
		acpRegister.getChildren().setAll(pane);
	    
	}
	
	
    @FXML
    private  void goToLogin(ActionEvent event) throws IOException 
    {
    	// opens login page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../Login/Login.fxml"));
		acpRegister.getChildren().setAll(pane);
    }


	
}
