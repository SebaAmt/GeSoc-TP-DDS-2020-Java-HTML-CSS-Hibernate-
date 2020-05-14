package dds.validaciones;

import dds.exception.PasswordException;

public class MasDe8Caracteres implements Validacion {

	public MasDe8Caracteres() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void validar(String username, String password) throws PasswordException {
		// TODO Auto-generated method stub

		if (password.length() < 8) { // Revisa la longitud minima de 8 caracteres del password
			throw new PasswordException("Inutilizable: no cumple con el minimo de caracteres!!");
		}
	}
}
