package controllers;

import java.util.HashMap;
import java.util.Map;

import com.mysql.cj.xdevapi.CreateIndexParams;
import model.usuario.CreadorDeUsuario;
import model.usuario.Usuario;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class UsuariosController {

    public ModelAndView getFormularioLogin(Request request, Response response) {
        return new ModelAndView(null, "formulario-login.html.hbs");
    }

    public Void iniciarSesion(Request request, Response response) {
        String password = request.queryParams("password");
        CreadorDeUsuario creador = new CreadorDeUsuario(null);
        String passwordEncriptada = creador.encriptarPassword(password);
        String username = request.queryParams("username");
        Usuario usuario = RepositorioUsuarios.instancia.listar().stream()
                .filter(u -> u.getPassword().equals(passwordEncriptada) && u.getUsername().equals(username)).findFirst().get();

        request.session().attribute("userName", usuario.getUsername());
        request.session().attribute("rol", usuario.getTipo());

        response.redirect("/");

        return null;
    }

    public ModelAndView getMensajes(Request request, Response response){
        SessionHelper.validarLogueado(request, response);
        Usuario usuarioLogueado = SessionHelper.getUsuarioLogueado(request);
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("mensajes", usuarioLogueado.getBandejaDeMensajes());
        modelo.put("bandejaVacia",usuarioLogueado.getBandejaDeMensajes().isEmpty());
        return new ModelAndView(modelo, "mensajes.html.hbs");
    }
}
