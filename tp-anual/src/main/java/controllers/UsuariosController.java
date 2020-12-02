package controllers;

import model.usuario.Usuario;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class UsuariosController {

    public ModelAndView getFormularioLogin(Request request, Response response) {
        return new ModelAndView(null, "formulario-login.html.hbs");
    }

    public Void iniciarSesion(Request request, Response response) {
        String password = request.queryParams("password");
        String username = request.queryParams("username");
        Usuario usuario = RepositorioUsuarios.instancia.getUsuarioConPassword(username, password);

        request.session().attribute("userName", usuario.getUsername());
        request.session().attribute("rol", usuario.getTipo());

        response.redirect("/");

        return null;
    }

    public ModelAndView getMensajes(Request request, Response response){
        Usuario usuarioLogueado = SessionHelper.getUsuarioLogueado(request);
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("mensajes", usuarioLogueado.getBandejaDeMensajes());
        modelo.put("bandejaVacia",usuarioLogueado.getBandejaDeMensajes().isEmpty());
        return new ModelAndView(modelo, "mensajes.html.hbs");
    }

    public Void cerrarSesion(Request request, Response response){
        request.session().invalidate();
        response.removeCookie("JSESSIONID");
        response.redirect("/login");
        return null;
    }
}
