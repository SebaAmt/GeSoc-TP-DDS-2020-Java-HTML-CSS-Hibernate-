package db;

import static org.junit.Assert.*;

import model.entidades.EntidadBase;
import model.entidades.EntidadJuridica;
import model.entidades.Organizacion;
import model.usuario.CreadorDeUsuario;
import model.usuario.TipoUsuario;
import model.usuario.Usuario;
import model.validacionesContrasenias.*;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;


public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}

	@Test
	public void contextUpWithTransaction() throws Exception {
		withTransaction(() -> {

			// Inicializo algunos objetos para ya tenerlos en la base y facilitar las pruebas/navegación por el sitio
			ValidadorDeContrasenias validador = new ValidadorDeContrasenias();
			CreadorDeUsuario creadorSimple = new CreadorDeUsuario(validador);

			Usuario usuarioPrueba = creadorSimple.crearUsuario("user1", "prueba", TipoUsuario.ESTANDAR);
			Organizacion valve = new Organizacion("Valve");
			EntidadJuridica blackMesa = new EntidadJuridica("Black Mesa SRL", "Black Mesa", "123456789", "Direccion 123", "123");
			EntidadBase aperture = new EntidadBase("Aperture Laboratories", "Hacemos portales");
			valve.agregarEntidadBase(aperture);
			valve.agregarEntidadJuridica(blackMesa);

			usuarioPrueba.asignarOrganizacion(valve);

			persist(blackMesa);
			persist(aperture);
			persist(valve);
			entityManager().persist(usuarioPrueba);
		});
	}

}
