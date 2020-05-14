package dds.validaciones;

import dds.exception.PasswordException;

public class NoConsecutivosORepetidos implements Validacion {

	public NoConsecutivosORepetidos() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void validar(String username, String password) throws PasswordException {

		for (int i = 0; i < password.length() - 1; i++) {
			int valor1 = password.charAt(i);
			int valor2 = password.charAt(i + 1);

			if (valor1 + 1 == valor2)
				throw new PasswordException("Hay caracteres Consecutivos!");
		}

		boolean esRepetido = true;

		for (int i = 0; i < password.length() - 1; i++) {
			int valor1 = password.charAt(i);
			int valor2 = password.charAt(i + 1);

			if (valor1 != valor2)
				esRepetido = false;
		}

		if (esRepetido) {
			throw new PasswordException("Hay caracteres repetidos!");
		}

	}

}
