package model.usuario;

import model.validacionesContrasenias.*;
import org.apache.commons.codec.digest.DigestUtils;

public class CreadorDeUsuario {
	ValidadorDeContrasenias validador;

	public CreadorDeUsuario(ValidadorDeContrasenias validador) {
		this.validador = validador;
	}

	public Usuario crearUsuario(String username, String password, TipoUsuario tipo) {

		this.validador.validarContrasenia(username, password);

		password = encriptarPassword(password);
		
		return new Usuario(username,password,tipo);
	}
	
	public String encriptarPassword(String password) {
		String textoEncriptadoConMD5 = DigestUtils.md5Hex(password);
		return textoEncriptadoConMD5;
	}

}

