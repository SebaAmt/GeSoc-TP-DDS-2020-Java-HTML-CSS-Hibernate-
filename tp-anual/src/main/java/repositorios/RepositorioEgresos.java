package repositorios;

import model.egreso.Egreso;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioEgresos implements WithGlobalEntityManager {

    public static RepositorioEgresos instancia = new RepositorioEgresos();

    public void agregarEgreso(Egreso egreso){
        entityManager().persist(egreso);
    }

}
