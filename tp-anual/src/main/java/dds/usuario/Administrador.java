package dds.usuario;

import dds.exception.PasswordException;

public class Administrador extends Usuario {

	public Administrador(String username, String password) throws PasswordException {
		super(username, password);
	}

}
