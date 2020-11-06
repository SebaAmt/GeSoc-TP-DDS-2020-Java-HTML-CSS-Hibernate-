package repositorios;

import model.entidades.Organizacion;
import model.usuario.TipoUsuario;
import model.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.ArrayList;
import java.util.List;

public class RepositorioOrganizaciones implements WithGlobalEntityManager {
    public static RepositorioOrganizaciones instancia = new RepositorioOrganizaciones();


    public List<Organizacion> listar() {
        return entityManager()
                .createQuery("from Organizacion", Organizacion.class) //
                .getResultList();
    }

    public Organizacion obtenerOrganizacionPorId(Long organizacionId){
        return entityManager().find(Organizacion.class, organizacionId);
    }

    public List<Organizacion> obtenerOrganizacionPorUsuario(Usuario usuario){
        if(usuario.getTipo().equals(TipoUsuario.ADMINISTRADOR))
            return this.listar();

        List<Organizacion> result = new ArrayList<Organizacion>();
        result.add(entityManager().find(Organizacion.class, usuario.getOrganizacion().getId()));
        return result;
    }

}
