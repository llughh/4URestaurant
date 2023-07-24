package application.model;

import java.util.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Usuario {
	
	private IntegerProperty idUser;
	private StringProperty Name;
	private StringProperty Username;
	private StringProperty Password;
	private IntegerProperty Rol;
	private StringProperty Email;
	private StringProperty Address;
	
	private static Usuario user;
	
	public Usuario(int idUser, String Name, String Username, String Password, int Rol, String Email, String Address) {
		this.idUser = new SimpleIntegerProperty(idUser);
		this.Name = new SimpleStringProperty(Name);
		this.Username = new SimpleStringProperty(Username);
		this.Password= new SimpleStringProperty(Password);
		this.Rol = new SimpleIntegerProperty(Rol);
		this.Email = new SimpleStringProperty(Email);
		this.Address = new SimpleStringProperty(Address);
	}
	
	public static Usuario getInstanceUser(int idUser, String Name, String Username, String Password, int Rol, String Email, String Address) {
		if(user == null) {
			user = new Usuario(idUser, Name, Username, Password, Rol, Email, Address);
		}
		return user;
	}
	
	public Integer getIdUser() {
		return idUser.get();
	}

	public void setIdUser(int idUser) {
		this.idUser = new SimpleIntegerProperty(idUser);
	}

	public String getName() {
		return Name.get();
	}

	public void setName(String name) {
		Name = new SimpleStringProperty(name);
	}

	public String getUsername() {
		return Username.get();
	}

	public void setUsername(String username) {
		Username = new SimpleStringProperty(username);
	}

	public String getPassword() {
		return Password.get();
	}

	public void setPassword(String password) {
		Password = new SimpleStringProperty(password);
	}

	public Integer getRol() {
		return Rol.get();
	}

	public void setRol(int rol) {
		Rol = new SimpleIntegerProperty(rol);
	}

	public String getEmail() {
		return Email.get();
	}

	public void setEmail(String email) {
		Email = new SimpleStringProperty(email);
	}

	public String getAddress() {
		return Address.get();
	}

	public void setAddress(String address) {
		Address = new SimpleStringProperty(address);
	}

}
