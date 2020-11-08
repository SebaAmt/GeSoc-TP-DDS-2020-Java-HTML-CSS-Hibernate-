package repositorios;

import model.egreso.Egreso;
import model.entidades.Entidad;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioEgresos implements WithGlobalEntityManager {

    public static RepositorioEgresos instancia = new RepositorioEgresos();

    public void agregarEgreso(Egreso egreso){
        entityManager().persist(egreso);
    }

    public Egreso getEgresoPorId(Long egresoId){
        return entityManager().find(Egreso.class, egresoId);
    }

}
