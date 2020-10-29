package model.validaciones;

public class ComprobarCaracteresConsecutivos extends Validacion {

	public ComprobarCaracteresConsecutivos(){
		super("Hay caracteres Consecutivos!");
	}

	@Override
	public boolean condicion(String username, String password) {

		for (int i = 0; i < password.length() - 1; i++) {
			int valor1 = password.charAt(i);
			int valor2 = password.charAt(i + 1);

			if (valor1 + 1 == valor2){
				return true;
			}
		}
		return false;

	}

}
