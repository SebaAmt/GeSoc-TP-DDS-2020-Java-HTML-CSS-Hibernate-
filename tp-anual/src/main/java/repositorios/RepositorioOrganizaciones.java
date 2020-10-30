package repositorios;

import model.entidades.Organizacion;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioOrganizaciones implements WithGlobalEntityManager {
    public static RepositorioOrganizaciones instancia = new RepositorioOrganizaciones();

    public List<Organizacion> listar() {
        return entityManager()//
                .createQuery("from Organizacion", Organizacion.class) //
                .getResultList();
    }

}
