package dds.usuario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import dds.exception.PasswordException;
import dds.validaciones.MasDe8Caracteres;
import dds.validaciones.NoConsecutivosORepetidos;
import dds.validaciones.Validacion;

public abstract class Usuario {

	private String username;
	private String password;

	public Usuario(String username, String password) throws PasswordException {

		try {

			validarContrasenia(username, password);

		} catch (Exception e) {
			throw new PasswordException(e.getMessage());
		}

	}

	public void validarContrasenia(String username, String password) throws PasswordException {

		List<Validacion> recomendaciones = Arrays.asList(new MasDe8Caracteres(), new NoConsecutivosORepetidos());

		try {
			verificarConTopPeoresContrasenias(password);// Verifica mediante un archivo txt de 10k peores contrasenias

			for (Validacion recomendacion : recomendaciones) {// Valida todas las recomendaciones/validaciones
				recomendacion.validar(username, password);
			}

			this.username = username;
			this.password = password;

		} catch (PasswordException e) {
			// TODO Auto-generated catch block
			throw new PasswordException(e.getMessage());
		}
	}

	private void verificarConTopPeoresContrasenias(String password) throws PasswordException {
		String texto = leerTxt("src\\main\\resources\\10k-most-common.txt");
		List<String> contrasenias = Arrays.asList(texto.split(","));

		boolean isExist = contrasenias.stream().anyMatch(contrasenia -> contrasenia.equals(password));
		if (isExist) {
			throw new PasswordException("La Password es debil");
		}
	}

	private String leerTxt(String direccion) {
		String texto = "";
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(direccion));
			String bfRead;
			String temp = "";
			while ((bfRead = buffer.readLine()) != null) {
				temp = temp + bfRead + ",";
			}

			texto = temp;

			buffer.close();

		} catch (Exception e) {
			System.err.println("No se encontro archivo");
		}
		return texto;
	}
}
