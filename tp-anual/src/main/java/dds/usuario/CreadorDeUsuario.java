package dds.usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dds.validaciones.*;
import org.apache.commons.codec.digest.DigestUtils;

import dds.exception.PasswordException;

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
	
	private String encriptarPassword(String password) {
		String textoEncriptadoConMD5 = DigestUtils.md5Hex(password);
		return textoEncriptadoConMD5;
	}

}

