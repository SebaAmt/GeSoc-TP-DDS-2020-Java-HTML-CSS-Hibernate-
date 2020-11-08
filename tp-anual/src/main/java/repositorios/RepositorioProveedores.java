package repositorios;

import model.egreso.Moneda;
import model.egreso.Proveedor;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioProveedores implements WithGlobalEntityManager {

    public static RepositorioProveedores instancia = new RepositorioProveedores();

    public List<Proveedor> obtenerProveedores(){
        return entityManager()
                .createQuery("from Proveedor", Proveedor.class) //
                .getResultList();
    }

    public Proveedor getProveedorPorId(Long id){
        return entityManager().find(Proveedor.class, id);
    }

}
