package controllers;


import model.categoria.Categoria;
import model.entidades.EntidadBase;
import model.entidades.EntidadJuridica;
import model.entidades.Organizacion;
import model.usuario.Usuario;
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
            List<EntidadBase> entidadesBase = organizacion.getEntidadesBase();
            List<EntidadJuridica> entidadesJuridicas = organizacion.getEntidadesJuridicas();
            if(organizacion == null){
                response.redirect("/error", 404); // not found
            }
            SessionHelper.validarOrganizacionUsuarioLogueado(request, response, Long.parseLong(id));
            Usuario usuarioLogueado = SessionHelper.getUsuarioLogueado(request);

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("organizacion", organizacion);
            modelo.put("entidadesBase", entidadesBase);
            modelo.put("entidadesJuridicas", entidadesJuridicas);
            
            return new ModelAndView(modelo, "detalle-organizacion.html.hbs");
        }
        catch (Exception ex){
            response.status(400);
            return null;
        }
    }
    
    public ModelAndView getOrganizacionConEntidadesFiltradas(Request request, Response response) {
    	Long organizacionId = Long.parseLong(request.params(":id"));
    	Long categoriaId = Long.parseLong(request.queryParams("filtro"));
    	
    	Organizacion organizacion = RepositorioOrganizaciones.instancia.obtenerOrganizacionPorId(organizacionId);
    	Categoria categoria = RepositorioCategorias.instancia.getCategoriaPorId(categoriaId);
    	
    	List<EntidadBase> entidadesBase =
    			categoriaId != null ?
    					organizacion.getEntidadesBase().stream().filter(e->e.getCategoria().equals(categoria)).collect(Collectors.toList()) :
    					organizacion.getEntidadesBase();
    	List<EntidadJuridica> entidadesJuridicas =
    			categoriaId != null ?
    					organizacion.getEntidadesJuridicas().stream().filter(e->e.getCategoria().equals(categoria)).collect(Collectors.toList()) :
    					organizacion.getEntidadesJuridicas();
    	
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("organizacion", organizacion);    	
        modelo.put("entidadesBase", entidadesBase);
        modelo.put("entidadesJuridicas", entidadesJuridicas);
    	
        return new ModelAndView(modelo, "detalle-organizacion.html.hbs");
    }
    
}
