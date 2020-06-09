package dds.validaciones;

import dds.exception.PasswordException;

public abstract class Validacion {
	protected String mensaje;

	protected Validacion(String mensaje){
		this.mensaje = mensaje;
	}


	public void validar(String username, String password){
		if(this.condicion(username, password))
			throw new PasswordException(mensaje);
	}

	protected abstract boolean condicion(String username, String password);
}
