package repositorios;

import model.egreso.Presupuesto;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioPresupuestos implements WithGlobalEntityManager {
    public static RepositorioPresupuestos instancia = new RepositorioPresupuestos();

    public void agregarPresupuesto(Presupuesto presupuesto){
        entityManager().persist(presupuesto);
    }
}
