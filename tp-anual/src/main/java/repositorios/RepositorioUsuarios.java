package repositorios;

import model.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioUsuarios implements WithGlobalEntityManager {

    public static RepositorioUsuarios instancia = new RepositorioUsuarios();

    public List<Usuario> listar() {
        return entityManager()//
                .createQuery("from Usuario", Usuario.class) //
                .getResultList();
    }

    public Usuario getByUsername(String username){
        return entityManager().find(Usuario.class, username);
    }

}
