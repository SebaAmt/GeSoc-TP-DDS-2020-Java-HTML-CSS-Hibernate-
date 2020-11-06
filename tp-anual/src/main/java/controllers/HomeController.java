package controllers;

import model.usuario.Usuario;
import repositorios.RepositorioOrganizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.Map;

public class HomeController {

    public ModelAndView getHome(Request request, Response response) {
        SessionHelper.validarLogueado(request, response);
        Usuario usuarioLogueado = SessionHelper.getUsuarioLogueado(request);
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("organizaciones", RepositorioOrganizaciones.instancia.obtenerOrganizacionPorUsuario(usuarioLogueado));

        return new ModelAndView(modelo, "index.html.hbs");
    }
}
