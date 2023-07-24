package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;

import application.bbdd.ConnectionBBDD;
import application.model.Usuario;
import application.model.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	
	@FXML TextField user;
	@FXML PasswordField password;
	@FXML Label errorMsg;
	@FXML Button login;
	@FXML Button register;
	@FXML AnchorPane ap_custom;
	
	static Vector<Usuario> us = new Vector<Usuario>();
	
	FXMLLoader loader = new FXMLLoader();
	URL location;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		this.login.setOnAction((Event) -> {
			ConnectionBBDD conn = new ConnectionBBDD();
			
			try {
				String sqlConsult = "SELECT * FROM user WHERE Username='" + this.user.getText().trim() + "' AND Password='" + this.password.getText().trim() + "'";
				ResultSet rs = conn.Consultar(sqlConsult);
				if (rs.next()) {
					UsuarioDAO uDAO = new UsuarioDAO();
					if(uDAO.login(this.user.getText().trim(), this.password.getText().trim())) {
						us.add(new Usuario(rs.getInt("idUser"), rs.getString("Name"), rs.getString("Username"), rs.getString("Password"), rs.getInt("Rol"), rs.getString("Email"), rs.getString("Address")));
						String rolAccess[] = {"ClientDashboard", "DeliveryDashboard", "ManagerDashboard"};
						int selectOpt = rs.getInt("Rol");
						if (rs.getInt("Rol") == 1 || rs.getInt("Rol") == 2) {
							Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Seleccione una opción para continuar.", new ButtonType("Cliente"), new ButtonType("Empleado"));
							a.setTitle("4U Restaurant");
							a.setHeaderText("¿Entrar como empleado o cliente?");
							Optional<ButtonType> confirmButton = a.showAndWait();
							if (confirmButton.get().getText().equals("Cliente"))
								selectOpt = 0;
							else
								selectOpt = rs.getInt("Rol");
						}
						location = LoginController.class.getResource("/application/views/"+ rolAccess[selectOpt] +".fxml");
						loader.setLocation(location);
						
						AnchorPane ap = loader.load();
						Stage stage = new Stage();
						Scene scene = new Scene(ap, 1000, 700);
						stage.setMinHeight(700);
						stage.setMinWidth(900);
						stage.setScene(scene);
						((Stage)ap_custom.getScene().getWindow()).close();
						stage.show();
					};
				}
				else {
					this.errorMsg.setText("Usuario y/o contraseña incorrectos");
				}
			}catch (Exception e) {
				System.out.println(e + ". -> En LoginController login.setOnAction");
			}
		});
		
		this.register.setOnAction((Event) -> {
			
			try {
				location = LoginController.class.getResource("/application/views/register.fxml");
				loader.setLocation(location);
				AnchorPane ap = loader.load();
				Stage stage = new Stage();
				Scene scene = new Scene(ap, 450, 500);
				stage.setMaxHeight(550);
				stage.setMaxWidth(950);
				stage.setScene(scene);
				stage.show();
				// Desarmamos la configuración del la ventana para login y register
				loader.setRoot(null);
				loader.setController(null);
				loader.setResources(null);
				loader.getNamespace().clear();
				this.errorMsg.setText("Usuario Creado con éxito");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e + ". -> En LoginController register.OnAction");
			}
		});
		
	}
	
}
