package dds.usuario.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dds.exception.PasswordException;
import dds.usuario.CreadorDeUsuario;
import dds.usuario.TipoUsuario;
import dds.usuario.Usuario;

public class UsuarioTest {
	TipoUsuario estandar = TipoUsuario.ESTANDAR;
	TipoUsuario administrador = TipoUsuario.ADMINISTRADOR;
	CreadorDeUsuario dios = new CreadorDeUsuario();
	
	@Test
	@DisplayName("Hay caracteres Repetidos")
	void contraseniaInvalidaPorLetrasRepetidas() {
		Exception exception = assertThrows(PasswordException.class, () -> dios.crearUsuario("Jesica", "vaamos1kpw", estandar));
		assertEquals("Hay caracteres repetidos!", exception.getMessage());
	}

	@Test
	@DisplayName("Hay caracteres Consecutivos")
	void contraseniaInvalidaPorLetrasConsecutivas() {
		Exception exception = assertThrows(PasswordException.class, () ->dios.crearUsuario("veronica", "veronica1234", estandar));
		assertEquals("Hay caracteres Consecutivos!", exception.getMessage());
	}

	@Test
	@DisplayName("No cumple el minimo de caracteres")
	void contraseniaNoSuperaLos8Caracteres() {
		Exception exception = assertThrows(PasswordException.class, () -> dios.crearUsuario("Mauro", "fulane",administrador));
		assertEquals("Inutilizable: no cumple con el minimo de caracteres!!", exception.getMessage());
	}

	@Test
	@DisplayName("Es contrasenia debil")
	void exceptionTesting() {
		Exception exception = assertThrows(PasswordException.class, () -> dios.crearUsuario("Mauro", "redskins", administrador)); // 'redskins' es una palabra tomada del archivo de las 10k contraseñas mas comunes
		assertEquals("La Password es debil", exception.getMessage());
	}

	@Test
	@DisplayName("Utiliza el nombre de usuario")
	void contraseniaInvalidaPorUtilizarNombreUsuario() {
		Exception exception = assertThrows(PasswordException.class, () -> dios.crearUsuario("jose", "joseramirez95",estandar));
		assertEquals("No puede incluir el nombre de usuario en la contrasenia", exception.getMessage());
	}



}