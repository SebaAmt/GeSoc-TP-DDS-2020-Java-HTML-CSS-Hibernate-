package dds.usuario;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import dds.exception.PasswordException;
import dds.validaciones.ComprobarSiPoseeMasDe8Caracteres;
import dds.validaciones.ComprobarSiPoseeCaracteresConsecutivosORepetidos;
import dds.validaciones.ComprobarSiIncluyeNombreDeUsuario;
import dds.validaciones.Validacion;
import dds.validaciones.ValidarTopPeoresContrasenias;

public abstract class Usuario {

	private String username;
	private String password;

	public Usuario(String username, String password) throws PasswordException {

		try {

			validarContrasenia(username, password);

		} catch (Exception e) {
			throw new PasswordException(e.getMessage());
		}

		this.password = encriptarPassword();
	}

	private String encriptarPassword() {
		String textoEncriptadoConMD5 = DigestUtils.md5Hex(password + username);
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

			this.username = username;
			this.password = password;

		} catch (PasswordException e) {

			throw new PasswordException(e.getMessage());
		}
	}

	public String password() {
		return password;
	}

}
