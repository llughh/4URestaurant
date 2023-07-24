package application.model;

import java.util.Date;

public class UsuarioPedidos {

	private String Name;
	private int idOrder;
	private String status;
	private Date time;
	private Double total;
	
	public UsuarioPedidos(String name, int idOrder, String status, Date time, Double total) {
		Name = name;
		this.idOrder = idOrder;
		this.status = status;
		this.time = time;
		this.total = total;
	}
	public UsuarioPedidos(Date time){
		Name = "N hay pedidos disponibles";
		this.idOrder = 0;
		this.status = "----";
		this.time = time;
		this.total = 0.0;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	
	public String toString() {
		return "UsuarioPedidos [Name=" + Name + ", idOrder=" + idOrder + ", status=" + status + ", time=" + time
				+ ", total=" + total + "]";
	}
	
}
