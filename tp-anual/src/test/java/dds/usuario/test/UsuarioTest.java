package dds.usuario.test;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dds.validaciones.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dds.exception.PasswordException;
import dds.usuario.CreadorDeUsuario;
import dds.usuario.TipoUsuario;

import java.io.IOException;


public class UsuarioTest {
	private CreadorDeUsuario dios;
	
	@BeforeEach
	public void init() throws IOException {
		ValidadorDeContrasenias validador = new ValidadorDeContrasenias();
		validador.agregarValidacion(new ComprobarCaracteresRepetidos());
		validador.agregarValidacion(new ComprobarCaracteresConsecutivos());
		validador.agregarValidacion(new ComprobarSiIncluyeNombreDeUsuario());
		validador.agregarValidacion(new ComprobarSiPoseeMasDe8Caracteres());
		validador.agregarValidacion(new ValidarTopPeoresContrasenias());
		dios = new CreadorDeUsuario(validador);
	}

	
	@Test
	@DisplayName("Hay caracteres Repetidos")
	void contraseniaInvalidaPorCaracteresRepetidos() {
		Exception exception = assertThrows(PasswordException.class, () -> dios.crearUsuario("Jesica", "vaamos1kpw", TipoUsuario.ESTANDAR));
		assertEquals("Hay caracteres repetidos!", exception.getMessage());
	}

	@Test
	@DisplayName("Hay caracteres Consecutivos")
	void contraseniaInvalidaPorCaracteresConsecutivos() {
		Exception exception = assertThrows(PasswordException.class, () ->dios.crearUsuario("veronica", "veronica1234", TipoUsuario.ESTANDAR));
		assertEquals("Hay caracteres Consecutivos!", exception.getMessage());
	}

	@Test
	@DisplayName("No cumple el minimo de caracteres")
	void contraseniaNoSuperaLos8Caracteres() {
		Exception exception = assertThrows(PasswordException.class, () -> dios.crearUsuario("Mauro", "fulane", TipoUsuario.ADMINISTRADOR));
		assertEquals("Inutilizable: no cumple con el minimo de caracteres!!", exception.getMessage());
	}

	@Test
	@DisplayName("Es contrasenia debil")
	void contraseniaEstaTopDePeoresContrasenias() {
		Exception exception = assertThrows(PasswordException.class, () -> dios.crearUsuario("Mauro", "redskins", TipoUsuario.ADMINISTRADOR)); // 'redskins' es una palabra tomada del archivo de las 10k contraseñas mas comunes, sin caracteres repetidos ni consecutivos
		assertEquals("La Password es debil", exception.getMessage());
	}

	@Test
	@DisplayName("Utiliza el nombre de usuario")
	void contraseniaInvalidaPorUtilizarNombreUsuario() {
		Exception exception = assertThrows(PasswordException.class, () -> dios.crearUsuario("jose", "joseramirez95", TipoUsuario.ESTANDAR));
		assertEquals("No puede incluir el nombre de usuario en la contrasenia", exception.getMessage());
	}

}