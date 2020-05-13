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

	public Usuario(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public void validarContrasenia(String password) {

		List<Validacion> recomendaciones = Arrays.asList(new MasDe8Caracteres(), new NoConsecutivosORepetidos());

		try {
			verificarConTopPeoresContrasenias(password);// Verifica mediante un archivo txt de 10k peores contraseñas

			for (Validacion recomendacion : recomendaciones) {// Valida todas las recomendaciones/validaciones
				recomendacion.validar(password);
			}

		} catch (PasswordException e) {
			// TODO Auto-generated catch block
			e.getMessage();
			e.printStackTrace();
		}
	}

	private void verificarConTopPeoresContrasenias(String password) throws PasswordException {
		String texto = leerTxt("src\\main\\resources\\10k-most-common.txt");
		List<String> contraseñas = Arrays.asList(texto.split(","));

		boolean isExist = contraseñas.stream().anyMatch(contraseña -> contraseña.equals(password));
		if (isExist) {
			throw new PasswordException("La Password es debil");
		}
	}

	private String leerTxt(String direccion) {
		String texto = "";
		try {
			BufferedReader bf = new BufferedReader(new FileReader(direccion));
			String bfRead;
			String temp = "";
			while ((bfRead = bf.readLine()) != null) {
				temp = temp + bfRead + ",";
			}

			texto = temp;

		} catch (Exception e) {
			System.err.println("No se encontro archivo");
		}
		return texto;
	}
}
