package app.ForgetPassword;

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

public class ForgetPasswordController implements Initializable
{
	
    @FXML
    private AnchorPane acpForgetPassword;

    @FXML
    private TextField txtStudentID;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private Label lblErrorMessage;
    
    Database database = new Database();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{		
		lblErrorMessage.setText("");
	}
    
	
	public void submit(ActionEvent event) throws IOException
	{
		String studentID = txtStudentID.getText();
		String newPassword = txtNewPassword.getText();
		String confirmPassword = txtConfirmPassword.getText();
		
		// if text boxes are empty
		if(studentID.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) 
		{
			lblErrorMessage.setText("All fields are required");
			return; // kicks you out of the method
		}
		
		// if both password do not match
		if (!newPassword.equals(confirmPassword)) 
		{
			lblErrorMessage.setText("Password does not match");
			return; // kicks you out of the method
		}
		
		// if student id does not exist in the database
		if (database.doesStudentIDExistInDataBase(studentID) == false)
		{
			lblErrorMessage.setText("Student ID does not exist");
			return;
		}
		
		// if the above condition has been met
		// update student's password
		database.PasswordUpdate(studentID, newPassword);
		
		// confirmation message box
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Event Managemet System - UOB");
		alert.setContentText("Password Successfully Changed.");
		alert.setHeaderText(null);
		alert.showAndWait();
				
		// opens login page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../Login/Login.fxml"));
		acpForgetPassword.getChildren().setAll(pane);

	}

	
	
	public void goToLogin(ActionEvent event) throws IOException
	{
		// opens login page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../Login/Login.fxml"));
		acpForgetPassword.getChildren().setAll(pane);
	}




}

