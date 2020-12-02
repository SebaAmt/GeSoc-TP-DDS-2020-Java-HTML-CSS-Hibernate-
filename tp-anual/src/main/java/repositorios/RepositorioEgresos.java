package repositorios;

import model.egreso.Egreso;
import model.entidades.Entidad;
import model.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;
import java.util.stream.Collectors;

public class RepositorioEgresos implements WithGlobalEntityManager {

    public static RepositorioEgresos instancia = new RepositorioEgresos();

    public void agregarEgreso(Egreso egreso){
        entityManager().persist(egreso);
    }

    public Egreso getEgresoPorId(Long egresoId){
        return entityManager().find(Egreso.class, egresoId);
    }

    public boolean esRevisor(Long egresoId, String username){
        List<String> usernames = this.getEgresoPorId(egresoId).getRevisores().stream().map(Usuario::getUsername).collect(Collectors.toList());
        return usernames.contains(username);
    }

}
