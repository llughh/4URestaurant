package application.controller;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

import application.bbdd.ConnectionBBDD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientOrderController implements Initializable {
	
	private ConnectionBBDD conn = new ConnectionBBDD();
	
	@FXML Label userLogged;
	@FXML Label confirmOrderQuestion;
	@FXML Label txtOrderConfirm;
	@FXML Label cooking;
	@FXML Label incoming;
	@FXML Label delivered;
	@FXML Label whereIsMyOrder;
	
	@FXML ProgressBar ProgressOrder;
	@FXML Button SeeOrders;
	@FXML Button myMap;
	
	@FXML private AnchorPane WindowClient;
	@FXML private Button CloseSession;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		this.userLogged.setText(" " + LoginController.us.get(0).getUsername());
		this.ProgressOrder.setStyle("-fx-accent: #00FF00;");
		UpdateProgress(null);
		
		
		/*====================================*/
		/* CLOSE SESION */
		/*====================================*/
		CloseSession.setOnAction(e -> {
			
			Alert a = new Alert(AlertType.NONE);
			a.setAlertType(AlertType.CONFIRMATION);
			a.setTitle("4U Restaurant");
			a.setHeaderText("Está usted cerrando sesión. ¿Quiere continuar?");
			a.setContentText("Gracias por visitar 4U Restaurant. ¡Vuelve pronto!");
			Optional<ButtonType> confirmButton = a.showAndWait();
			if (confirmButton.get() == ButtonType.OK) {
				Stage stage = (Stage) this.WindowClient.getScene().getWindow();
				stage.close();
			}
		});
		
		this.myMap.setOnAction(e -> {
			String sql_follow = "SELECT s.* FROM sensor as s, pedidosensor as ps WHERE ps.idSensor=s.idSensor AND ps.idPedido=(SELECT p.idPedido FROM pedido as p WHERE p.idUser="+LoginController.us.get(0).getIdUser()+" ORDER BY p.date DESC limit 1) ORDER BY s.id DESC LIMIT 1";
			try {
				ResultSet rs = conn.Consultar(sql_follow);
				String latd = "", longd = "";
				if(rs.next()) {
					latd = rs.getBigDecimal("latitud") + "";
					longd = rs.getBigDecimal("longitud") + "";
				}
				Desktop desktop = Desktop.getDesktop();
				URI uri;
				uri = new URI("http://maps.google.com/maps?q=" + longd + "," + latd);
			 	desktop.browse(uri);
				
			} catch (Exception e_map) {
				System.out.println(e_map);
			}
			
		});
	}
	
	
	/*====================================*/
	/* UPDATE STATUS */
	/*====================================*/
	private void selectOption(boolean val) {
		if(val) {
			this.confirmOrderQuestion.setText("Pedido confirmado");
			this.txtOrderConfirm.setText("Gracias por confiar en nosotros. Tu pedido \r\n"
					+ "ha sido confirmado con éxito.");
		}
		else {
			this.confirmOrderQuestion.setText("No hay pedidos disponibles");
			this.txtOrderConfirm.setText("No tienes ningún pedido creado hoy.");
		}
		
		this.ProgressOrder.setVisible(val);
		this.cooking.setVisible(val);
		this.incoming.setVisible(val);
		this.delivered.setVisible(val);
		this.myMap.setVisible(val);
		this.whereIsMyOrder.setVisible(val);
	}
	

	@FXML
	private void UpdateProgress(ActionEvent e) {
		try {
			String sqlConsult = "SELECT * FROM pedido WHERE idUser="+LoginController.us.get(0).getIdUser()+" ORDER BY date DESC LIMIT 1";
			ResultSet rs = conn.Consultar(sqlConsult);
			
			
			String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			
			if (rs.next() && String.valueOf(rs.getDate("Date")).equals(currentTime)) {
				this.selectOption(true);
				if (rs.getString("status").equals("Pending")) {
					this.ProgressOrder.setProgress(0.25);
				}
				else if (rs.getString("status").equals("Coming")) {
					this.ProgressOrder.setProgress(0.5);
				}
				else {
					this.ProgressOrder.setProgress(1);
				}
			}
			
			else {
				this.selectOption(false);
			}
			
		} catch (SQLException e1) {
			// e.printStackTrace();
			System.out.println(e + ". -> En ClientOrderController");
		}
	}

}
