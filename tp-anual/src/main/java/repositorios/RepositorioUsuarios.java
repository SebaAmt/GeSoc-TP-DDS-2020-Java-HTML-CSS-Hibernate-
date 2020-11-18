package repositorios;

import model.usuario.CreadorDeUsuario;
import model.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;
import java.util.stream.Collectors;

public class RepositorioUsuarios implements WithGlobalEntityManager {

    public static RepositorioUsuarios instancia = new RepositorioUsuarios();

    public List<Usuario> listar() {
        return entityManager()//
                .createQuery("from Usuario", Usuario.class) //
                .getResultList();
    }

    public Usuario getUsuarioConPassword(String username, String password){
        CreadorDeUsuario creador = new CreadorDeUsuario(null);
        String passwordEncriptada = creador.encriptarPassword(password);

        return this.listar().stream().filter(u -> u.getPassword().equals(passwordEncriptada) && u.getUsername().equals(username)).findFirst().get();
    }

    public Usuario getByUsername(String username){
        return entityManager().find(Usuario.class, username);
    }

}
