package dds.usuario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	private String username;
	private String password;
	@Enumerated
	private TipoUsuario tipo;
	@ElementCollection
	private List<String> bandejaDeMensajes = new ArrayList<>();

	public Usuario() {
		
	}
	
	public Usuario(String username, String password, TipoUsuario tipo) {
		this.username = username;
		this.password = password;
		this.tipo = tipo;
	}

	public String password() {
		return password;
	}

	public void nuevoMensaje(String mensaje){
		this.bandejaDeMensajes.add(mensaje);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public List<String> getBandejaDeMensajes() {
		return bandejaDeMensajes;
	}

	public void setBandejaDeMensajes(List<String> bandejaDeMensajes) {
		this.bandejaDeMensajes = bandejaDeMensajes;
	}
	
	@Override
	public String toString() {
		return "Usuario{" +
				"username='" + username + '\'' +
				", tipo=" + tipo +
				'}';
	}
}
