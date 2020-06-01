package dds.usuario;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import dds.exception.PasswordException;
import dds.validaciones.ComprobarSiIncluyeNombreDeUsuario;
import dds.validaciones.ComprobarSiPoseeCaracteresConsecutivosORepetidos;
import dds.validaciones.ComprobarSiPoseeMasDe8Caracteres;
import dds.validaciones.Validacion;
import dds.validaciones.ValidarTopPeoresContrasenias;

public class CreadorDeUsuario {

	public void crearUsuario(String username, String password, TipoUsuario tipo) {
		
		try {
			validarContrasenia(username, password);
		} 
		catch (Exception e) {
			throw new PasswordException(e.getMessage());
		}

		password = encriptarPassword(password);
		
		new Usuario(username,password,tipo);
	}
	
	private String encriptarPassword(String password) {
		String textoEncriptadoConMD5 = DigestUtils.md5Hex(password);
		return textoEncriptadoConMD5;
	}

	public void validarContrasenia(String username, String password) throws PasswordException {

		List<Validacion> recomendaciones = Arrays.asList(new ComprobarSiPoseeMasDe8Caracteres(),
				new ComprobarSiPoseeCaracteresConsecutivosORepetidos(), new ComprobarSiIncluyeNombreDeUsuario(),
				new ValidarTopPeoresContrasenias());

		try {

			for (Validacion recomendacion : recomendaciones) {// Valida todas las recomendaciones/validaciones
				recomendacion.validar(username, password);
			}

		} catch (PasswordException e) {

			throw new PasswordException(e.getMessage());
		}
	}


}

