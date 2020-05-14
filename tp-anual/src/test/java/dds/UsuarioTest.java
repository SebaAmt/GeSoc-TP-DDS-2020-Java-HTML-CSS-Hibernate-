package dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dds.exception.PasswordException;
import dds.usuario.Administrador;
import dds.usuario.Estandar;

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

}