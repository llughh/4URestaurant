package application.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import application.bbdd.ConnectionBBDD;
import application.model.Pedido;
import application.model.PedidoProductos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class seeOrderEmployeeController implements Initializable {
	
	@FXML AnchorPane WindowClient;
	
	private ConnectionBBDD conn = new ConnectionBBDD();
	
	private ObservableList<PedidoProductos> list = FXCollections.observableArrayList();
	@FXML private TableView<PedidoProductos> tableOrder;
	@FXML private TableColumn<PedidoProductos, String> name;
	@FXML private TableColumn<PedidoProductos, Integer> quantity;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		String sql_consult = "SELECT pr.name, pp.quantity FROM productos as pr, pedidoproductos as pp WHERE pp.idProducto = pr.idProducto AND pp.idPedido = " + ManagerDashboardController.UserOrder.get(0).getIdOrder();
		
		try {
			
			ResultSet rs = conn.Consultar(sql_consult);
			
			while (rs.next()) {
				list.add(new PedidoProductos(rs.getString("name"), rs.getInt("quantity")));
			}
			this.name.setCellValueFactory(new PropertyValueFactory<PedidoProductos, String>("name"));
			this.quantity.setCellValueFactory(new PropertyValueFactory<PedidoProductos, Integer>("quantity"));
			this.tableOrder.setItems(list);
		} catch (Exception e) {
			System.out.println("Fail in listOrder -> " + e);
		}
		
	}

}
