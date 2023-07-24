package application.model;


public class Sensor {
	
	private int id;
	private String name;
	private String address;
	private String status;
	private Float f_hum;
	private Float f_temp;
	private Float c_hum;
	private Float c_temp;
	
	public Sensor(int id, String name, String address, String status, Float f_hum, Float f_temp, Float c_hum, Float c_temp) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.status = status;
		this.f_hum = f_hum;
		this.f_temp = f_temp;
		this.c_hum = c_hum;
		this.c_temp = c_temp;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Float getF_hum() {
		return f_hum;
	}

	public void setF_hum(Float f_hum) {
		this.f_hum = f_hum;
	}

	public Float getF_temp() {
		return f_temp;
	}

	public void setF_temp(Float f_temp) {
		this.f_temp = f_temp;
	}

	public Float getC_hum() {
		return c_hum;
	}

	public void setC_hum(Float c_hum) {
		this.c_hum = c_hum;
	}

	public Float getC_temp() {
		return c_temp;
	}

	public void setC_temp(Float c_temp) {
		this.c_temp = c_temp;
	}

	@Override
	public String toString() {
		return "Sensor [name=" + name + ", address=" + address + ", status=" + status + ", f_hum=" + f_hum + ", f_temp="
				+ f_temp + ", c_hum=" + c_hum + ", c_temp=" + c_temp + "]";
	}

}
