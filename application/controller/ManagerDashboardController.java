package application.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;

import application.bbdd.ConnectionBBDD;
import application.model.Pedido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

public class ManagerDashboardController implements Initializable {
	
	private FXMLLoader loader = new FXMLLoader();
	private URL location;
	
	private ConnectionBBDD conn = new ConnectionBBDD();
	
	@FXML private AnchorPane WindowClient;
    @FXML private Button CloseSession;
    @FXML private Label userLogged;
    
    @FXML private AreaChart areaChart;
    private XYChart.Series<String, Integer> series = new XYChart.Series<>();
    @FXML private CategoryAxis yAxis;
    @FXML private NumberAxis xAxis;
    
    @FXML private Button employee;
    @FXML private Button products;
    @FXML private Button orders;
    
    @FXML private Label errorMessage;
    
    private ObservableList<Pedido> list = FXCollections.observableArrayList();
    
    @FXML private TableView<Pedido> tableProducts;
    @FXML private TableColumn<Pedido, Integer> id;
    @FXML private TableColumn<Pedido, String> name;
    @FXML private TableColumn<Pedido, String> address;
    @FXML private TableColumn<Pedido, String> status;
    @FXML private TableColumn<Pedido, Date> time;
    
    
    @FXML private Button pending;
    @FXML private Button coming;
    @FXML private Button delivered;
    @FXML private Button updateTable;
    @FXML private Button seeOrder;
    
    
    static Vector<Pedido> UserOrder = new Vector<Pedido>();
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		this.userLogged.setText(LoginController.us.get(0).getUsername());
		
		this.UpdateOrders(null);
		this.tableProducts.setItems(list);
		
		
		this.orders.setOnAction(e -> {
			try {
				this.location = LoginController.class.getResource("/application/views/seeAllOrdersManager.fxml");
				this.loader.setLocation(location);
				AnchorPane ap = loader.load();
				Stage stage = new Stage();
				Scene scene = new Scene(ap, 560, 660);
				stage.setMinHeight(400);
				stage.setMinWidth(450);
				stage.setScene(scene);
				stage.show();
				loader.setRoot(null);
				loader.setController(null);
				loader.setResources(null);
				loader.getNamespace().clear();
			} catch (Exception eSeeAll) {
				this.errorMessage.setText("Ha ocurrido un error en SellAllOrders -> " + eSeeAll);
			}
		});
		
		
		this.pending.setOnAction(e -> {
			try {
				this.UpdateSelectValue("Pending");
			} catch (Exception e2) {
				this.errorMessage.setText("No has seleccionado ningún valor");
			}
		});
		
		this.coming.setOnAction(e -> {
			try {
				this.UpdateSelectValue("Coming");
			} catch (Exception e2) {
				this.errorMessage.setText("No has seleccionado ningún valor");
			}
		});
		
		this.delivered.setOnAction(e -> {
			try {
				this.UpdateSelectValue("Delivered");
			} catch (Exception e2) {
				this.errorMessage.setText("No has seleccionado ningún valor");
			}
		});
		
		this.seeOrder.setOnAction(e -> {
			try {
				if(this.tableProducts.getSelectionModel().getSelectedItem().getIdOrder() != 0) {
					UserOrder.add(this.tableProducts.getSelectionModel().getSelectedItem());
					this.location = LoginController.class.getResource("/application/views/seeOrderEmployee.fxml");
					this.loader.setLocation(location);
					AnchorPane ap = loader.load();
					Stage stage = new Stage();
					Scene scene = new Scene(ap, 400, 550);
					stage.setMaxHeight(600);
					stage.setMaxWidth(700);
					stage.setScene(scene);
					stage.show();
					loader.setRoot(null);
					loader.setController(null);
					loader.setResources(null);
					loader.getNamespace().clear();
				}
				else
					this.errorMessage.setText("No has seleccionado ningún valor");
			} catch (Exception eSeeOrder) {
				this.errorMessage.setText("No has seleccionado ningún valor");
			}
			UserOrder.removeAllElements();
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
		String update_data = "UPDATE pedido SET status = '" + s + "' WHERE idPedido="+this.tableProducts.getSelectionModel().getSelectedItem().getIdOrder()+"";
		try {
			conn.SaveInfo(update_data);
		}
		catch (Exception e) {
			System.out.println("Problem in ManagerDashboard Orders Update Delivered --> " + e);
		}
		this.UpdateOrders(null);
	}
	
	
	@FXML
	private void UpdateAreaChart(ActionEvent e) {
		LocalDate current_date = LocalDate.now();
		int current_Year = current_date.getYear();
		
		this.areaChart.getData().clear();
		this.yAxis.getCategories().clear();
		this.series.getData().clear();
		try {
			ResultSet rs;
			for (int i = 1; i <= 12; i++) {
				String sql_data_year = "SELECT * FROM pedido WHERE YEAR(date) = "+ current_Year +" AND MONTH(date) = " + i;
				rs = conn.Consultar(sql_data_year);
				int pedidos = 0;
				while (rs.next()) {
					pedidos++;
				}
				series.getData().add(new XYChart.Data<>(new DateFormatSymbols().getMonths()[i - 1].substring(0, 3), pedidos));
			}
		} catch (Exception e2) {
			System.out.println("Error in AreaChart -> " + e2);
		}
		
		this.areaChart.getData().add(series);
		
	}
	
	
	@FXML
	private void UpdateOrders(ActionEvent e) {
		String sql_orders = "SELECT u.Name, u.Address, p.idUser, p.idPedido, p.status, p.date FROM user as u, pedido as p WHERE p.status != 'Delivered' AND u.idUser=p.idUser ORDER BY p.date ASC";
		
		try {
			ResultSet rs = conn.Consultar(sql_orders);
			list.clear();
			
			while (rs.next()) {
				int cont = 0;
				boolean val = true;
				while (cont < list.size() && val) {
					if (list.get(cont).getIdOrder() == rs.getInt("idPedido"))
						val = false;
					cont++;
				}
				if (val)
					list.add(new Pedido(rs.getString("Name"), rs.getString("Address"), rs.getInt("idPedido"), rs.getString("status"), rs.getDate("date")));	
			}
			if(list.size() < 1) {
				list.add(new Pedido(new Date()));
			}
			
			this.id.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("idOrder"));
			this.name.setCellValueFactory(new PropertyValueFactory<Pedido, String>("name"));
			this.address.setCellValueFactory(new PropertyValueFactory<Pedido, String>("address"));
			this.status.setCellValueFactory(new PropertyValueFactory<Pedido, String>("statusOrder"));
			this.time.setCellValueFactory(new PropertyValueFactory<Pedido, Date>("time"));
			
			this.UpdateAreaChart(null);
			
		}
		catch (Exception e1) {
			System.out.println("Problem in ManagerDashboard Orders ActionEvent --> " + e1);
		}
	}
	

}
