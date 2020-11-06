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
            Usuario usuarioPrueba = new Usuario("user1", "7815696ecbf1c96e6894b779456d330e", TipoUsuario.ESTANDAR); //password: asd
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

            Usuario usuarioPrueba2 = new Usuario("user2", "7815696ecbf1c96e6894b779456d330e", TipoUsuario.ESTANDAR); //password: asd
            Organizacion lucasArts = new Organizacion("Lucas Arts");
            EntidadJuridica monkeyIsland = new EntidadJuridica("Monkey Island SRL", "Monkey Island", "784521365", "Melee 123", "887");
            EntidadBase maniacMansion = new EntidadBase("Maniac Mansion SA", "Una descripción loca");
            lucasArts.agregarEntidadBase(maniacMansion);
            lucasArts.agregarEntidadJuridica(monkeyIsland);
            usuarioPrueba2.asignarOrganizacion(lucasArts);

            persist(monkeyIsland);
            persist(maniacMansion);
            persist(lucasArts);
            persist(usuarioPrueba2);

            persist(new Usuario("admin", "7815696ecbf1c96e6894b779456d330e", TipoUsuario.ADMINISTRADOR)); //password: asd
        });
    }

}