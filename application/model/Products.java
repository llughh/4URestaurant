package application.model;

public class Products {
	
	private int idProduct;
	private String name;
	private String description;
	private int quantity;
	private Float price;
	private double iva;
	private String typeProduct;
	
	
	public Products(int idProduct, String name, String description, int quantity, Float price, Double iva, String typeProduct) {
		super();
		this.idProduct = idProduct;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.iva = iva;
		this.typeProduct = typeProduct.substring(3); // Elimina los 3 primeros caracteres: "1 - Desayuno" --> "Desayuno"
	}


	public int getIdProduct() {
		return idProduct;
	}


	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public Float getPrice() {
		return price;
	}


	public void setPrice(Float price) {
		this.price = price;
	}
	
	public Double getIva() {
		return iva;
	}
	
	public void setIva(Double iva) {
		this.iva = iva;
	}

	public void setTypeProduct(String typeProduct) {
		this.typeProduct = typeProduct.substring(3);
	}
	
	public String getTypeProduct() {
		return typeProduct;
	}
	
	public String toString() {
		return "ID: " + this.idProduct +"\nNAME: " + this.name +"\nDESCRIPTION: " + this.description + "\nQUANTITY: " + this.quantity + "\nPRICE: " + this.price + "\nIVA: " + this.iva + "\nTYPE: " + this.typeProduct;
	}
}
