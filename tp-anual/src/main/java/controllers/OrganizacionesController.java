package controllers;

import model.categoria.Categoria;
import model.entidades.EntidadBase;
import model.entidades.EntidadJuridica;
import model.entidades.Organizacion;
import model.usuario.Usuario;
import model.validacionesEgresos.ValidacionEgreso;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import repositorios.RepositorioOrganizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrganizacionesController implements WithGlobalEntityManager {

    public ModelAndView getOrganizaciones(Request request, Response response){
        SessionHelper.validarLogueado(request, response);
        Usuario usuarioLogueado = SessionHelper.getUsuarioLogueado(request);
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("organizaciones", RepositorioOrganizaciones.instancia.obtenerOrganizacionPorUsuario(usuarioLogueado));

        return new ModelAndView(modelo, "organizaciones.html.hbs");
    }

    public ModelAndView getDetalleOrganizacion(Request request, Response response){
        String id = request.params(":id");
        try{
            Organizacion organizacion = RepositorioOrganizaciones.instancia.obtenerOrganizacionPorId(Long.parseLong(id));
            if(organizacion == null){
                response.redirect("/error", 404); // not found
            }

            SessionHelper.validarOrganizacionUsuarioLogueado(request, response, Long.parseLong(id));
            Usuario usuarioLogueado = SessionHelper.getUsuarioLogueado(request);

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("organizacion", organizacion);

            return new ModelAndView(modelo, "detalle-organizacion.html.hbs");
        }
        catch (Exception ex){
            response.status(400);
            return null;
        }
    }
}
