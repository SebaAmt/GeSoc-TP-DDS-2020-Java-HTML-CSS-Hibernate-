package dds.validaciones;

import dds.exception.PasswordException;

public interface Validacion {

	public void validar(String username, String password) throws PasswordException;
}
