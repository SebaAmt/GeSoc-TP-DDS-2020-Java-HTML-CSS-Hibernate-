package model.validaciones;

public class ComprobarSiIncluyeNombreDeUsuario extends Validacion {

	public ComprobarSiIncluyeNombreDeUsuario(){
		super("No puede incluir el nombre de usuario en la contrasenia");
	}

	@Override
	public boolean condicion(String username, String password) {
		return password.contains(username);
	}

}
