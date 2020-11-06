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
		withTransaction(() -> {	});
	}

}
