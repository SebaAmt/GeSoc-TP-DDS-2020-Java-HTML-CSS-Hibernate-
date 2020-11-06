package controllers;

import model.entidades.Organizacion;
import model.usuario.TipoUsuario;
import model.usuario.Usuario;
import repositorios.RepositorioUsuarios;
import spark.Request;
import spark.Response;

public class SessionHelper {

    public static void validarLogueado(Request request, Response response) {
        Usuario usuario = SessionHelper.getUsuarioLogueado(request);
        if(usuario == null)
            response.redirect("/login");
    }

    public static Usuario getUsuarioLogueado(Request request) {
        String username = request.session().attribute("userName");
        Usuario usuario = null;

        if(username != null){
            usuario = RepositorioUsuarios.instancia.getByUsername(username);
        }
        return usuario;
    }

    public static void validarOrganizacionUsuarioLogueado(Request request, Response response, Long organizacionId){
        SessionHelper.validarLogueado(request, response);
        Usuario usuarioLogueado = SessionHelper.getUsuarioLogueado(request);

        if(usuarioLogueado.getTipo().equals(TipoUsuario.ADMINISTRADOR))
            return;

        if(!usuarioLogueado.getOrganizacion().getId().equals(organizacionId))
            response.status(403); //forbidden

    }
}
