package dds.usuario;

import java.util.Arrays;
import java.util.List;
import dds.usuario.TipoUsuario;

public class Usuario {

	private String username;
	private String password;
	private TipoUsuario tipo;

	public Usuario(String username, String password, TipoUsuario tipo) {
		this.username = username;
		this.password = password;
		this.tipo = tipo;
	} 

	public String password() {
		return password;
	}

}
