package repositorios;


import model.entidades.Entidad;
import model.entidades.EntidadBase;
import model.entidades.EntidadJuridica;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;


public class RepositorioEntidades implements WithGlobalEntityManager {

    public static RepositorioEntidades instancia = new RepositorioEntidades();

    public void agregarEntidad(EntidadBase entidad){
        entityManager().persist(entidad);
    }

    public void agregarEntidad(EntidadJuridica entidad){
        entityManager().persist(entidad);
    }

    public Entidad obtenerEntidadPorId(Long entidadId){
        return entityManager().find(Entidad.class, entidadId);
    }

}
