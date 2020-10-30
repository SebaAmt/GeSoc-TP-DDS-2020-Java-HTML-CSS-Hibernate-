package model.validacionesContrasenias;

public class ComprobarSiPoseeMasDe8Caracteres extends ValidacionContrasenia {

	public ComprobarSiPoseeMasDe8Caracteres(){
		super("Inutilizable: no cumple con el minimo de caracteres!!");
	}

	@Override
	public boolean condicion(String username, String password) {
		return password.length() < 8;
	}
}
