package application.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import application.bbdd.ConnectionBBDD;
import application.model.UsuarioPedidos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class seeAllOrdersManagerController implements Initializable {
	
	private ConnectionBBDD conn = new ConnectionBBDD();

	@FXML AnchorPane WindowClient;
	
	private ObservableList<UsuarioPedidos> list = FXCollections.observableArrayList();
	@FXML TableView<UsuarioPedidos> tableOrders;
	@FXML TableColumn<UsuarioPedidos, Integer> idOrder;
	@FXML TableColumn<UsuarioPedidos, String> user;
	@FXML TableColumn<UsuarioPedidos, String> status;
	@FXML TableColumn<UsuarioPedidos, Date> dateOrder;
	@FXML TableColumn<UsuarioPedidos, Double> pay;
	
	@FXML Label total;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		LocalDate current_date = LocalDate.now();
		
		int current_Year = current_date.getYear();
		int current_month = current_date.getMonthValue();
		
		String sql_consult = "SELECT p.idPedido, p.status, p.date, p.total, u.Name "
				+ "FROM pedido as p, user as u "
				+ "WHERE YEAR(p.date)= "+ current_Year +" AND MONTH(p.date)= "+ current_month +" AND p.idUser = u.idUser";
	
		try {
			
			ResultSet rs = conn.Consultar(sql_consult);
			Double cont = 0.0;
			while (rs.next()) {
				list.add(new UsuarioPedidos(rs.getString("Name"), rs.getInt("idPedido"), rs.getString("status"), rs.getDate("date"), rs.getDouble("total")));
				cont += rs.getDouble("total");
			}
			
			if (list.size() < 1) {
				list.add(new UsuarioPedidos(new Date()));
			}
			
			this.idOrder.setCellValueFactory(new PropertyValueFactory<UsuarioPedidos, Integer>("idOrder"));
			this.user.setCellValueFactory(new PropertyValueFactory<UsuarioPedidos, String>("Name"));
			this.status.setCellValueFactory(new PropertyValueFactory<UsuarioPedidos, String>("status"));
			this.dateOrder.setCellValueFactory(new PropertyValueFactory<UsuarioPedidos, Date>("time"));
			this.pay.setCellValueFactory(new PropertyValueFactory<UsuarioPedidos, Double>("total"));
			
			this.tableOrders.setItems(list);
			
			this.total.setText(cont.toString() + "€");
			
		} catch (Exception e) {
			System.out.println("Fail in listAllOrders -> " + e);
		}
		
	}
	
}
