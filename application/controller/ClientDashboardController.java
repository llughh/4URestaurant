package application.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;

import application.bbdd.ConnectionBBDD;
import application.model.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientDashboardController implements Initializable {
	
	FXMLLoader loader = new FXMLLoader();
	URL location;
	
	@FXML private ChoiceBox<String> selectTypeProduct;
	Vector <String> mySelectOption = new Vector<String>();
	
	private ObservableList<Products> list = FXCollections.observableArrayList();
	private ObservableList<Products> listFinal = FXCollections.observableArrayList();
	
	@FXML private TableView<Products> tableProducts;
	
    @FXML private TableColumn<Products, String> name;
    @FXML private TableColumn<Products, String> description;
    @FXML private TableColumn<Products, Float> price;
    @FXML private TableColumn<Products, Double> priceIva;
    @FXML private TableColumn<Products, String> typeProduct;
    
    @FXML private Label userLogged;
    
    @FXML private Button addProduct;
    
    
    @FXML private TableView<Products> tableProductsFinal;
    
    @FXML private TableColumn<Products, String> nameFinal;
    @FXML private TableColumn<Products, Float> priceFinal;
    @FXML private TableColumn<Products, Integer> quantityFinal;
    
    @FXML private Button deleteProduct;
    @FXML private Button confirmPedido;
    
    @FXML private Label errorMessage;
    @FXML private Label totalPriceNow;
    
    @FXML private AnchorPane WindowClient;
    @FXML private Button CloseSession;
    
    @FXML private Button FollowOrder;
    
    private float precioTotal = 0;
	
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		ConnectionBBDD conn = new ConnectionBBDD();
		String sqlConsult = "SELECT * FROM productos ORDER BY typeProduct ASC";
		try {
			ResultSet rs = conn.Consultar(sqlConsult);
			while (rs.next()) {
				list.add(new Products(rs.getInt("idProducto"), rs.getString("name"), rs.getString("description"),  0, rs.getFloat("price"), rs.getDouble("iva"), rs.getString("typeProduct")));
			}
			
			mySelectOption.add("Todos los productos");
			for (Products p : list) {
				if (!mySelectOption.contains(p.getTypeProduct()))
					mySelectOption.add(p.getTypeProduct());
			}
			this.selectTypeProduct.setItems(FXCollections.observableArrayList(mySelectOption));
			
			name.setCellValueFactory(new PropertyValueFactory<Products, String>("name"));
			description.setCellValueFactory(new PropertyValueFactory<Products, String>("description"));
			price.setCellValueFactory(new PropertyValueFactory<Products, Float>("price"));
			priceIva.setCellValueFactory(new PropertyValueFactory<Products, Double>("iva"));
			typeProduct.setCellValueFactory(new PropertyValueFactory<Products, String>("typeProduct"));
			
			this.tableProducts.setItems(list);
			this.userLogged.setText(LoginController.us.get(0).getUsername());
			this.dataValuePrice();
			
			
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println(e + ". -> En ClientDashboardController");
		}
		
		/*===========================*/
		/* ADD ITEM SELECTION TO BUY */
		/*===========================*/
		nameFinal.setCellValueFactory(new PropertyValueFactory<Products, String>("name"));
		priceFinal.setCellValueFactory(new PropertyValueFactory<Products, Float>("price"));
		quantityFinal.setCellValueFactory(new PropertyValueFactory<Products, Integer>("quantity"));
		
		this.addProduct.setOnAction(e -> {
			
			if (listFinal.size() > 0) {
				
				if (listFinal.contains(this.tableProducts.getSelectionModel().getSelectedItem())) {
					this.tableProducts.getSelectionModel().getSelectedItem().setQuantity(this.tableProducts.getSelectionModel().getSelectedItem().getQuantity() + 1);
					this.tableProductsFinal.refresh();
				}
				else{
					this.tableProducts.getSelectionModel().getSelectedItem().setQuantity(this.tableProducts.getSelectionModel().getSelectedItem().getQuantity() + 1);
					this.listFinal.add(this.tableProducts.getSelectionModel().getSelectedItem());
				}
				
			}
			else {
				this.tableProducts.getSelectionModel().getSelectedItem().setQuantity(this.tableProducts.getSelectionModel().getSelectedItem().getQuantity() + 1);
				this.listFinal.add(this.tableProducts.getSelectionModel().getSelectedItem());
			}
			
			this.dataValuePrice();
			this.tableProductsFinal.setItems(listFinal);
		});
		
		
		/*====================================*/
		/* DELETE ITEM (1X1) TABLE BUY CLIENT */
		/*====================================*/
		this.deleteProduct.setOnAction(e -> {
			try {
				if (this.listFinal.size() > 0) {
					if (this.tableProductsFinal.getSelectionModel().getSelectedItem().getQuantity() > 1) {
						this.tableProductsFinal.getSelectionModel().getSelectedItem().setQuantity(this.tableProductsFinal.getSelectionModel().getSelectedItem().getQuantity()-1);
					}
					else {
						this.listFinal.remove(this.tableProductsFinal.getSelectionModel().getSelectedItem());
					}
					this.tableProductsFinal.refresh();
					
					this.dataValuePrice();
				}
			}
			catch (Exception ex) {
				this.errorMessage.setText("No has seleccionado ningún valor");
			}
			
		});
		
		
		/*====================================*/
		/* CONFIRM ORDER CLIENT */
		/*====================================*/
		this.confirmPedido.setOnAction(e -> {
			
			if(this.listFinal.size() > 0) {
				float precio = 0;
				
				for (Products p : listFinal) {
					precio += ((p.getPrice()) * ((p.getIva() / 100) + 1)) * p.getQuantity();
				}
				
				Alert a = new Alert(AlertType.NONE);
				a.setAlertType(AlertType.CONFIRMATION);
				
				a.setTitle("4U Restaurant");
				a.setHeaderText("Su pedido de "+ String.format("%.2f", precio) +"€ (IVA INCLUIDO) va a ser tramitado. ¿Quiere continuar?");
				Optional<ButtonType> confirmButton = a.showAndWait();
				if (confirmButton.get() == ButtonType.OK) {
					String dateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
					
					try {
						// Si todo va bien, habremos confirmado el pedido del cliente
						String sqlInsert = "INSERT INTO pedido (idUser, status, date, total) VALUES ("+LoginController.us.get(0).getIdUser()+", 'Pending', '"+ dateNow +"', "+precio+")";
						conn.SaveInfo(sqlInsert);
						// Selecciona un solo valor de la consulta (LIMIT 1), eligiendo el último valor introducido por el cliente. Pos eso usamos ORDER BY .... DESC
						String sqlConsultId = "SELECT idPedido FROM pedido WHERE idUser="+LoginController.us.get(0).getIdUser()+" AND date='"+ dateNow +"' ORDER BY idPedido DESC LIMIT 1";
						ResultSet rs_idPedido = conn.Consultar(sqlConsultId);
						if(rs_idPedido.next()) {
							for (Products p : listFinal) {
								String InsertPedidos = "INSERT INTO pedidoproductos (idPedido, idProducto, quantity) VALUES ("+rs_idPedido.getInt("idPedido")+", "+ p.getIdProduct() +", "+ p.getQuantity() +")";
								conn.SaveInfo(InsertPedidos);
							}
							String sqlConsultSensor = "INSERT INTO pedidosensor VALUES (1, "+rs_idPedido.getInt("idPedido")+")";
							conn.SaveInfo(sqlConsultSensor);
							try {
								location = LoginController.class.getResource("/application/views/OrderConfirm.fxml");
								loader.setLocation(location);
								AnchorPane ap = loader.load();
								Stage stage = new Stage();
								Scene scene = new Scene(ap, 1000, 700);
								stage.setMinWidth(800);
								stage.setMinHeight(650);
								stage.setMaxWidth(900);
								stage.setMaxHeight(750);
								stage.setScene(scene);
								((Stage)WindowClient.getScene().getWindow()).close();
								stage.show();
							}
							catch (Exception e2) {
								this.errorMessage.setText("Ha ocurrido un error al intentar hacer un seguimiento al pedido");
							}
							
						}
					} catch (SQLException e1) {
						// Si hay un error con conexión a la bbdd arroja este error
						this.errorMessage.setText("No se pudo confirmar el pedido");
					}
				}
				else {
					// Si presionas cancelar, paras el pedido
					this.errorMessage.setText("Haz cancelado el pedido.");
				}
			}
			else {
				// Si la lista está vacia arroja este Mensaje
				this.errorMessage.setText("Tu lista está vacía.");
			}
		});
		
		
		
		/*====================================*/
		/* FILTER LIST */
		/*====================================*/
		FilteredList<Products> filterList = new FilteredList<>(list, b -> true);
		
		
		selectTypeProduct.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			
			filterList.setPredicate(Products -> {
				
				if(newValue.isEmpty() || newValue == null || newValue.equals("Todos los productos")) {
					return true;
				}
				
				if (Products.getTypeProduct().indexOf(newValue) > -1) {
					return true;
				}
				else {
					return false;
				}
				
			});
		});
		
		SortedList<Products> sortedList = new SortedList<>(filterList);
		sortedList.comparatorProperty().bind(tableProducts.comparatorProperty());
		
		tableProducts.setItems(sortedList);
		
		
		
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
		
		
		/*====================================*/
		/* SEGUIMIENTO DEL PEDIDO */
		/*====================================*/
		this.FollowOrder.setOnAction(e -> {
			try {
				location = LoginController.class.getResource("/application/views/OrderConfirm.fxml");
				loader.setLocation(location);
				AnchorPane ap = loader.load();
				Stage stage = new Stage();
				Scene scene = new Scene(ap, 1000, 700);
				stage.setMinWidth(800);
				stage.setMinHeight(650);
				stage.setMaxWidth(900);
				stage.setMaxHeight(750);
				stage.setScene(scene);
				((Stage)WindowClient.getScene().getWindow()).close();
				stage.show();
			} catch (Exception e4) {
				System.out.println("No se ha podido seguir el pedido");
			}
		});
		
		
	}
	
	
	
	
	public void dataValuePrice() {
		this.precioTotal = 0;
		if (this.listFinal.size() > 0) {
			for (Products p : listFinal) {
				this.precioTotal += ((p.getPrice()) * ((p.getIva() / 100) + 1)) * p.getQuantity();
			}
			this.totalPriceNow.setText("Total (con IVA): " + String.format("%.2f", this.precioTotal) + "€");
		}
		else {
			this.totalPriceNow.setText("0€");
		}
	}

}
