package dds.usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import dds.exception.PasswordException;
import dds.validaciones.ComprobarSiIncluyeNombreDeUsuario;
import dds.validaciones.ComprobarCaracteresConsecutivos;
import dds.validaciones.ComprobarCaracteresRepetidos;
import dds.validaciones.ComprobarSiPoseeMasDe8Caracteres;
import dds.validaciones.Validacion;
import dds.validaciones.ValidarTopPeoresContrasenias;

public class CreadorDeUsuario {

	public Usuario crearUsuario(String username, String password, TipoUsuario tipo) {
		
		try {
			validarContrasenia(username, password);
		} 
		catch (Exception e) {
			throw new PasswordException(e.getMessage());
		}

		password = encriptarPassword(password);
		
		return new Usuario(username,password,tipo);
	}
	
	private String encriptarPassword(String password) {
		String textoEncriptadoConMD5 = DigestUtils.md5Hex(password);
		return textoEncriptadoConMD5;
	}

	public void validarContrasenia(String username, String password) {

		List<Validacion> recomendaciones = new ArrayList();
		recomendaciones.add(new ComprobarCaracteresConsecutivos());
		recomendaciones.add(new ComprobarCaracteresRepetidos());
		recomendaciones.add(new ComprobarSiPoseeMasDe8Caracteres());
		recomendaciones.add(new ComprobarSiIncluyeNombreDeUsuario());
		recomendaciones.add(new ValidarTopPeoresContrasenias());

		for (Validacion recomendacion : recomendaciones) {// Valida todas las recomendaciones/validaciones
			recomendacion.validar(username, password);
		}
	}
}

