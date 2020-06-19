package dds.usuario.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import dds.validaciones.*;
import dds.validacionesEgresos.EgresoEnBaseAPresupuestoCorrecto;
import dds.validacionesEgresos.EgresoTieneCantidadMinimaDePresupuestos;
import dds.validacionesEgresos.ValidacionEgreso;
import dds.validacionesEgresos.ValidadorEgresos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dds.documentoComercial.DocumentoComercial;
import dds.egreso.CreadorEgreso;
import dds.egreso.Egreso;
import dds.egreso.EstadoEgreso;
import dds.egreso.Item;
import dds.egreso.Presupuesto;
import dds.egreso.Proveedor;
import dds.exception.PasswordException;
import dds.initialize.Initialize;
import dds.usuario.CreadorDeUsuario;
import dds.usuario.TipoUsuario;
import dds.usuario.Usuario;

public class UsuarioTest {
	private CreadorDeUsuario dios;
	Initialize prueba = new Initialize();
	ValidadorEgresos validadorDeEgreso;
	List <ValidacionEgreso> validacionesDeEgreso;
	List<Presupuesto> presupuestosValor50;
	List<Presupuesto> presupuestosValor600;

	@BeforeEach
	public void init() {
		ValidadorDeContrasenias validador = new ValidadorDeContrasenias();
		validador.agregarValidacion(new ComprobarCaracteresRepetidos());
		validador.agregarValidacion(new ComprobarCaracteresConsecutivos());
		validador.agregarValidacion(new ComprobarSiIncluyeNombreDeUsuario());
		validador.agregarValidacion(new ComprobarSiPoseeMasDe8Caracteres());
		validador.agregarValidacion(new ValidarTopPeoresContrasenias());
		dios = new CreadorDeUsuario(validador);
		
		prueba.setDePrueba();
		
		validacionesDeEgreso = Arrays.asList(new EgresoEnBaseAPresupuestoCorrecto(),new EgresoTieneCantidadMinimaDePresupuestos());
		validadorDeEgreso = new ValidadorEgresos(Arrays.asList(prueba.egreso1),validacionesDeEgreso);
		presupuestosValor50 = Arrays.asList(new Presupuesto(prueba.proveedor1, prueba.documento2, prueba.items2));
		presupuestosValor600 = Arrays.asList(new Presupuesto(prueba.proveedor1, prueba.documento2, prueba.items1));
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


	//ENTREGA 2
	
	@Test
	@DisplayName("Obtiene mensaje rechazado si no cumple con la cantidad minima de presupuestos")
	void EnviaUsuarioMensajeRechazadoMinimoPresupuestosCargados() {
		Usuario jesica = dios.crearUsuario("Jesica", "fulan159", TipoUsuario.ESTANDAR);		
		Egreso egreso = new Egreso(LocalDate.of(2020,5,15), prueba.proveedor1, prueba.documento2, prueba.medioDePago1, prueba.items2, prueba.moneda1, jesica, presupuestosValor50, false, EstadoEgreso.PENDIENTE, null);

		validadorDeEgreso = new ValidadorEgresos(Arrays.asList(egreso),validacionesDeEgreso);
		validadorDeEgreso.validarEgresosPendientes();
		
		assertEquals("El Egreso " + egreso + " fue RECHAZADO: El egreso no cumple con la cantidad minima de presupuestos cargados", jesica.getBandejaDeMensajes().get(0));
	}
	
	@Test
	@DisplayName("Obtiene mensaje de rechazo si el egreso no se corresponde con alguno de los presupuestos cargados (sin criterio)")
	void EnviaUsuarioMensajeRechazadoPorValorTotal() {
		Usuario jesica = dios.crearUsuario("Jesica", "fulan159", TipoUsuario.ESTANDAR);

		Egreso egreso = new Egreso(LocalDate.of(2020,5,15), prueba.proveedor1, prueba.documento2, prueba.medioDePago1, prueba.items2, prueba.moneda1, jesica, presupuestosValor600, false, EstadoEgreso.PENDIENTE, null);

		validadorDeEgreso = new ValidadorEgresos(Arrays.asList(egreso),validacionesDeEgreso);
		validadorDeEgreso.validarEgresosPendientes();

		assertEquals("El Egreso " + egreso + " fue RECHAZADO: El egreso no se corresponde con ninguno de los presupuestos cargados", jesica.getBandejaDeMensajes().get(0));
	}

}