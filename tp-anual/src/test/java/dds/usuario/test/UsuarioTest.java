package dds.usuario.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dds.exception.PasswordException;
import dds.usuario.Administrador;
import dds.usuario.Estandar;
import dds.usuario.Usuario;

public class UsuarioTest {
	

	@Test
	@DisplayName("Hay caracteres Repetidos")
	void contraseniaInvalidaPorLetrasRepetidas() {
		Exception exception = assertThrows(PasswordException.class, () -> new Estandar("Jesica", "aaaaaaaaaaa"));
		assertEquals("Hay caracteres repetidos!", exception.getMessage());
	}

	@Test
	@DisplayName("Hay caracteres Consecutivos")
	void contraseniaInvalidaPorLetrasConsecutivas() {
		Exception exception = assertThrows(PasswordException.class, () -> new Estandar("veronica", "veronica1234"));
		assertEquals("Hay caracteres Consecutivos!", exception.getMessage());
	}

	@Test
	@DisplayName("No cumple el minimo de caracteres")
	void contraseniaNoSuperaLos8Caracteres() {
		Exception exception = assertThrows(PasswordException.class, () -> new Administrador("Mauro", "fulano"));
		assertEquals("Inutilizable: no cumple con el minimo de caracteres!!", exception.getMessage());
	}

	@Test
	@DisplayName("Es contrasenia debil")
	void exceptionTesting() {
		Exception exception = assertThrows(PasswordException.class, () -> new Administrador("Mauro", "password"));
		assertEquals("La Password es debil", exception.getMessage());
	}

	@Test
	@DisplayName("Utiliza el nombre de usuario")
	void contraseniaInvalidaPorUtilizarNombreUsuario() {
		Exception exception = assertThrows(PasswordException.class, () -> new Estandar("jose", "joseramirez95"));
		assertEquals("No puede incluir el nombre de usuario en la contrasenia", exception.getMessage());
	}

	@Test
	@DisplayName("Contrasenia encriptada")
	public void testIntegrante1() throws PasswordException {
		Usuario pablo = new Estandar("pabloM","rojocai7L");
		assertNotEquals("rojocai7L", pablo.password());
	}


}