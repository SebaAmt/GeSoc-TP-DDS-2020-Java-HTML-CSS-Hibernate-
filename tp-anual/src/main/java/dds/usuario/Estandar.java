package dds.usuario;

import dds.exception.PasswordException;

public class Estandar extends Usuario {

	public Estandar(String username, String password) throws PasswordException {
		super(username, password);
	}

}
