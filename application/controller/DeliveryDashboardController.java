package application.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;

import application.bbdd.ConnectionBBDD;
import application.model.Pedido;
import application.model.Sensor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeliveryDashboardController implements Initializable {
	
	private ConnectionBBDD conn = new ConnectionBBDD();
	
	@FXML private AnchorPane WindowClient;
    @FXML private Button CloseSession;
    @FXML private Label userLogged;
    
    @FXML private Label errorMessage;
    
    private ObservableList<Sensor> list = FXCollections.observableArrayList();
    
    @FXML private TableView<Sensor> tableProducts;
    @FXML private TableColumn<Sensor, String> name;
    @FXML private TableColumn<Sensor, String> address;
    @FXML private TableColumn<Sensor, String> status;
    @FXML private TableColumn<Sensor, Float> f_hum;
    @FXML private TableColumn<Sensor, Float> f_temp;
    @FXML private TableColumn<Sensor, Float> c_hum;
    @FXML private TableColumn<Sensor, Float> c_temp;
    
    @FXML private Button delivered;
    @FXML private Button updateTable;
    
    
    static Vector<Pedido> UserOrder = new Vector<Pedido>();
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		this.userLogged.setText(LoginController.us.get(0).getUsername());
		this.UpdateOrders(null);
		this.tableProducts.setItems(list);
		
		
		this.delivered.setOnAction(e -> {
			try {
				this.UpdateSelectValue("Delivered");
			} catch (Exception e2) {
				this.errorMessage.setText("No has seleccionado ningún valor");
			}
		});
		
		
		/*====================================*/
		/* CLOSE SESION */
		/*====================================*/
		CloseSession.setOnAction(e -> {
			
			Alert a = new Alert(AlertType.NONE);
			a.setAlertType(AlertType.CONFIRMATION);
			a.setTitle("4U Restaurant");
			a.setHeaderText("Está usted cerrando sesión. ¿Quiere continuar?");
			a.setContentText("Gracias por trabajar en 4U Restaurant.");
			Optional<ButtonType> confirmButton = a.showAndWait();
			if (confirmButton.get() == ButtonType.OK) {
				Stage stage = (Stage) this.WindowClient.getScene().getWindow();
				stage.close();
			}
			
		});
		
	}
	

	private void UpdateSelectValue(String s) {
		this.errorMessage.setText("");
		String update_data = "UPDATE pedido SET status = '" + s + "' WHERE idPedido="+this.tableProducts.getSelectionModel().getSelectedItem().getId()+"";
		try {
			conn.SaveInfo(update_data);
		}
		catch (Exception e) {
			System.out.println("Problem in DeliveryDashboard Orders Update Delivered --> " + e);
		}
		this.UpdateOrders(null);
	}
	
	
	
	@FXML
	private void UpdateOrders(ActionEvent e) {
		String sql_sensor = "SELECT p.idPedido, u.Name, u.Address, p.status, s.humF, s.tempF, s.humC, s.tempC FROM user as u, pedido as p, sensor as s, pedidosensor as ps WHERE u.idUser=p.idUser AND p.status = 'Coming' AND p.idPedido=ps.idPedido AND ps.idSensor=s.idSensor ORDER BY s.id DESC";
		
		try {
			ResultSet rs = conn.Consultar(sql_sensor);
			list.clear();
			
			while (rs.next()) {
				int cont = 0;
				boolean val = true;
				while (cont < list.size() && val) {
					if (list.get(cont).getId() == rs.getInt("idPedido"))
						val = false;
					cont++;
				}
				if (val)
					list.add(new Sensor(rs.getInt("idPedido"), rs.getString("Name"), rs.getString("Address"), rs.getString("status"), rs.getFloat("humF"), rs.getFloat("tempF"), rs.getFloat("humC"), rs.getFloat("tempC")));	
			}
			
			this.name.setCellValueFactory(new PropertyValueFactory<Sensor, String>("name"));
			this.address.setCellValueFactory(new PropertyValueFactory<Sensor, String>("address"));
			this.status.setCellValueFactory(new PropertyValueFactory<Sensor, String>("status"));
			this.f_hum.setCellValueFactory(new PropertyValueFactory<Sensor, Float>("f_hum"));
			this.f_temp.setCellValueFactory(new PropertyValueFactory<Sensor, Float>("f_temp"));
			this.c_hum.setCellValueFactory(new PropertyValueFactory<Sensor, Float>("c_hum"));
			this.c_temp.setCellValueFactory(new PropertyValueFactory<Sensor, Float>("c_temp"));
			
		}
		catch (Exception e1) {
			System.out.println("Problem in ManagerDashboard Orders ActionEvent --> " + e1);
		}
	}
	

}
