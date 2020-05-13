package dds.validaciones;

import dds.exception.PasswordException;

public interface Validacion {

	public void validar(String password) throws PasswordException;
}
