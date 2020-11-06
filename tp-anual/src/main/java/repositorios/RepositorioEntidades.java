package repositorios;

import model.entidades.EntidadBase;
import model.entidades.EntidadJuridica;
import model.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioEntidades implements WithGlobalEntityManager {

    public static RepositorioEntidades instancia = new RepositorioEntidades();

    public List<EntidadBase> obtenerEntidadesBaseDeOrganizacion(Long organizacionId){
        return entityManager()
                .createQuery("from EntidadBase where organizacion_id = :orgId").setParameter("orgId", organizacionId)
                .getResultList();
    }

    public List<EntidadJuridica> obtenerEntidadesJuridicasDeOrganizacion(Long organizacionId){
        return entityManager()
                .createQuery("from EntidadJuridica where organizacion_id = :orgId").setParameter("orgId", organizacionId)
                .getResultList();
    }

}
