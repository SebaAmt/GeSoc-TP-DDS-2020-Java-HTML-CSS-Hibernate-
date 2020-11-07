package repositorios;


import model.entidades.Entidad;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioEntidades implements WithGlobalEntityManager {

    public static RepositorioEntidades instancia = new RepositorioEntidades();

    public Entidad obtenerEntidadPorId(Long organizacionId){
        return entityManager().find(Entidad.class, organizacionId);
    }

}
