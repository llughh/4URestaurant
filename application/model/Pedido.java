package application.model;

import java.util.Date;

public class Pedido {
	
	private int idOrder;
	private int idUser;
	private String name;
	private String address;
	private String statusOrder;
	private Date time;
	
	public Pedido(String name, String address, int idUser, int idOrder, String statusOrder, Date time) {
		this.idOrder = idOrder;
		this.idUser = idUser;
		this.name = name;
		this.address = address;
		this.statusOrder = statusOrder;
		this.time = time;
	}
	
	public Pedido(String name, String address, int idOrder, String statusOrder, Date time) {
		this.idOrder = idOrder;
		this.name = name;
		this.address = address;
		this.statusOrder = statusOrder;
		this.time = time;
	}
	public Pedido(Date time) {
		this.idOrder = 0;
		this.name = "No hay pedidos activos ahora mismo";
		this.address = "-----";
		this.statusOrder = "---";
		this.time = time;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}
	
	public int getIdUser() {
		return idUser;
	}

	public void getIdUser(int IdUser) {
		this.idUser = IdUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatusOrder() {
		return statusOrder;
	}

	public void setStatusOrder(String statusOrder) {
		this.statusOrder = statusOrder;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Pedido [idOrder=" + idOrder + ", idUser=" + idUser + ", name=" + name + ", address=" + address
				+ ", statusOrder=" + statusOrder + ", time=" + time + "]";
	}

}
