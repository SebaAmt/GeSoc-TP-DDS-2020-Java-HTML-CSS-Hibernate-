package controllers;


import model.categoria.Categoria;
import model.entidades.EntidadBase;
import model.entidades.EntidadJuridica;
import model.entidades.Organizacion;
import model.usuario.Usuario;
import org.eclipse.jetty.http.HttpStatus;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import repositorios.RepositorioCategorias;
import repositorios.RepositorioOrganizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

            List<EntidadBase> entidadesBase;
            List<EntidadJuridica> entidadesJuridicas;

            if(request.queryParams("filtro") != null){
                Categoria categoria = RepositorioCategorias.instancia.getCategoriaPorId(Long.parseLong(request.queryParams("filtro")));
                entidadesBase = organizacion.getEntidadesBase().stream().filter(e->e.getCategoria().equals(categoria)).collect(Collectors.toList());
                entidadesJuridicas = organizacion.getEntidadesJuridicas().stream().filter(e->e.getCategoria().equals(categoria)).collect(Collectors.toList());
            } else {
                entidadesBase = organizacion.getEntidadesBase();
                entidadesJuridicas = organizacion.getEntidadesJuridicas();
            }

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("organizacion", organizacion);
            modelo.put("entidadesBase", entidadesBase);
            modelo.put("entidadesJuridicas", entidadesJuridicas);
            modelo.put("cantidadCategorias", organizacion.getCategorias().stream().count());
            modelo.put("cantidadEtiquetas", organizacion.getEtiquetasDisponibles().stream().count());
            modelo.put("cantidadValidaciones", organizacion.getValidacionesEgresos().stream().count());
            
            return new ModelAndView(modelo, "detalle-organizacion.html.hbs");
        }
        catch (Exception ex){
            response.status(HttpStatus.BAD_REQUEST_400);
            return null;
        }
    }

}
