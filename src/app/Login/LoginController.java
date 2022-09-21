package app.Login;

import java.net.URL;
import java.util.ResourceBundle;
import app.Model.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController implements Initializable
{
	
    @FXML
    private AnchorPane acpLogin;

    @FXML
    private TextField txtStudentID;

    @FXML
    private PasswordField txPassword;

    @FXML
    private Label lblErrorMessage;
	
	
	Database database = new Database();
	

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		lblErrorMessage.setText("");
		
	}

	// login button
	@FXML
	private void Login(ActionEvent event) throws Exception 
	{
		String studentID = txtStudentID.getText();
		String password = txPassword.getText();
		
		if (database.onLoginSuccess(studentID, password) == true)
		{	
			// opens home page
			AnchorPane pane = FXMLLoader.load(getClass().getResource("../Home/Home.fxml"));
			acpLogin.getChildren().setAll(pane);
		}
		else 
		{
			// error message
			lblErrorMessage.setText("Incorrect Student ID or Password");
		}	
		
	
	}

	
	// Forget password link
	@FXML
	private void ForgetPassword(ActionEvent event) throws Exception 
	{
		// opens forget password page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../ForgetPassword/ForgetPassword.fxml"));
		acpLogin.getChildren().setAll(pane);
	}
	
	
	// Register link
	@FXML
	private void Register(ActionEvent event) throws Exception 
	{
		// opens register page
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../Register/Register.fxml"));
		acpLogin.getChildren().setAll(pane);
	}
	
	








}
