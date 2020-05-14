package dds.validaciones;

import dds.exception.PasswordException;

public class NoPuedeIncluirNombreUsuario implements Validacion{

	@Override
	public void validar(String username, String password) throws PasswordException {
		// TODO Auto-generated method stub
		if(password.contains(username)) {
			throw new PasswordException("No puede incluir el nombre de usuario en la contrasenia");
		}
	}

}
