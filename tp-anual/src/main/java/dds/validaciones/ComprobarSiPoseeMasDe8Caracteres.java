package dds.validaciones;

import dds.exception.PasswordException;

public class ComprobarSiPoseeMasDe8Caracteres implements Validacion {

	public ComprobarSiPoseeMasDe8Caracteres() {

	}

	@Override
	public void validar(String username, String password) {

		if (password.length() < 8) { // Revisa la longitud minima de 8 caracteres del password
			throw new PasswordException("Inutilizable: no cumple con el minimo de caracteres!!");
		}
	}
}
