package dds.validaciones;

import dds.exception.PasswordException;

public class ComprobarSiIncluyeNombreDeUsuario implements Validacion {

	@Override
	public void validar(String username, String password) {
		if (password.contains(username)) {
			throw new PasswordException("No puede incluir el nombre de usuario en la contrasenia");
		}
	}

}
