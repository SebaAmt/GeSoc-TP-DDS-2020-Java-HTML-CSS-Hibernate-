package controllers;

import model.entidades.EntidadBase;
import model.entidades.EntidadJuridica;
import model.entidades.Organizacion;
import model.usuario.CreadorDeUsuario;
import model.usuario.TipoUsuario;
import model.usuario.Usuario;
import model.validacionesContrasenias.ValidadorDeContrasenias;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

/**
 * Ejecutar antes de levantar el servidor por primera vez
 *
 * @author flbulgarelli
 */

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

    public static void main(String[] args) {
        new Bootstrap().run();
    }

    public void run() {
        withTransaction(() -> {
            Usuario usuarioPrueba = new Usuario("user1", "c893bad68927b457dbed39460e6afd62", TipoUsuario.ESTANDAR); //password: prueba
            Organizacion valve = new Organizacion("Valve");
            EntidadJuridica blackMesa = new EntidadJuridica("Black Mesa SRL", "Black Mesa", "123456789", "Direccion 123", "123");
            EntidadBase aperture = new EntidadBase("Aperture Laboratories", "Hacemos portales");
            valve.agregarEntidadBase(aperture);
            valve.agregarEntidadJuridica(blackMesa);
            usuarioPrueba.asignarOrganizacion(valve);

            persist(blackMesa);
            persist(aperture);
            persist(valve);
            persist(usuarioPrueba);
            persist(new Usuario("admin", "c893bad68927b457dbed39460e6afd62", TipoUsuario.ADMINISTRADOR)); //password: prueba
        });
    }

}