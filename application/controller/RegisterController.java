package application.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import application.bbdd.ConnectionBBDD;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController implements Initializable{
	
	@FXML Label errorMsg;
	@FXML TextField name;
	@FXML TextField username;
	@FXML PasswordField password1;
	@FXML PasswordField password2;
	@FXML TextField email;
	@FXML TextField address;
	@FXML Button register;
	
	@FXML AnchorPane windowRegister;
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		this.register.setOnAction((event) -> {
			if(this.password1.getText().equals(this.password2.getText())) {
				if (this.email.getText().contains("@")) {
					ConnectionBBDD conn = new ConnectionBBDD();
					String dateNow = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
					String sqlConsult = 
							"INSERT INTO user (Name, Username, Password, Rol, Email, Address, Date) VALUES ('"+ this.name.getText().trim() +"', "
							+ "'"+ this.username.getText().trim() +"', '"+ this.password1.getText().trim() +"', "
									+ ""+ 0 +", '"+ this.email.getText().trim() +"', '"+ this.address.getText().trim() +"', '" + dateNow + "')";
					try {
							conn.SaveInfo(sqlConsult);
							Stage stage = (Stage) this.windowRegister.getScene().getWindow();
							stage.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println(e + ". -> En RegistrerController");
					}
				}
				else {
					this.errorMsg.setText("El correo electrónico no es válido.");
				}
			}
			else {
				this.errorMsg.setText("Las contraseñas no coinciden");
			}
		});
		
	}
	
}