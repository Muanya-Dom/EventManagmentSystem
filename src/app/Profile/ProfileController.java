package app.Profile;

import java.net.URL;
import java.util.ResourceBundle;

import app.Model.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;


public class ProfileController implements Initializable
{
    @FXML
    private Text lblFirstName;

    @FXML
    private Text lblLastName;

    @FXML
    private Text lblStudentID;

    @FXML
    private Text lblOrganisationRight;
	
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		lblFirstName.setText(Database.FIRST_NAME);
		lblLastName.setText(Database.LAST_NAME);
		lblOrganisationRight.setText(Database.ORGANISATION_RIGHT);
		
		String studentID = Integer.toString(Database.STUDENTID);
		lblStudentID.setText(studentID);
		
	}
		

}
