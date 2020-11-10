package controllers;

import model.categoria.Categoria;
import model.entidades.Entidad;
import model.entidades.EntidadBase;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import repositorios.RepositorioCategorias;
import repositorios.RepositorioEntidades;
import repositorios.RepositorioOrganizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class EntidadesController implements WithGlobalEntityManager, TransactionalOps {

	
	public ModelAndView getFormCreacionEntidadJuridica(Request request, Response response){
        String idOrg = request.params(":idOrg");
        try{
            SessionHelper.validarOrganizacionUsuarioLogueado(request, response, Long.parseLong(idOrg));

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("idOrganizacion", Long.parseLong(idOrg));
            modelo.put("categorias", RepositorioOrganizaciones.instancia.obtenerCategoriasDeOrganizacion((Long.parseLong(idOrg))));
            
            return new ModelAndView(modelo, "form-creacion-entidad-juridica.html.hbs");
        }
        catch (Exception ex){
            response.status(400);
            return null;
        }
    }
	
	
	
	
    public ModelAndView getDetalleEntidad(Request request, Response response){
        String idOrg = request.params(":idOrg");
        String idEntidad = request.params(":idEntidad");
        try{
            Entidad entidad = RepositorioEntidades.instancia.obtenerEntidadPorId(Long.parseLong(idEntidad));
            if(entidad == null){
                response.redirect("/error", 404); // not found
            }

            SessionHelper.validarOrganizacionUsuarioLogueado(request, response, Long.parseLong(idOrg));

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("entidad", entidad);

            return new ModelAndView(modelo, "detalle-entidad.html.hbs");
        }
        catch (Exception ex){
            response.status(400);
            return null;
        }
    }
    
    
    public ModelAndView createEntidadBase(Request request, Response response){
    	String nombre = request.queryParams("nombre");
    	String descripcion = request.queryParams("descripcion");
    	EntidadBase entidad = new EntidadBase(nombre, descripcion);

    	if(request.queryParams("categoria") != null) {
    		Categoria categoria = RepositorioCategorias.instancia.getCategoriaPorId(Long.parseLong(request.queryParams("categoria")));
    		entidad.setCategoria(categoria);
    	}
    	
        withTransaction(() ->{
        	RepositorioEntidades.instancia.agregarEntidadBase(entidad);
        });
    	return null;    	
    }
    
    public ModelAndView createEntidadJuridica(Request request, Response response) {
    	String name = request.queryParams("nombre");
    	String razonSocial = request.queryParams("nombre");
    	String cuit = request.queryParams("nombre");
    	String codigoPostal = request.queryParams("codigoPostal");
    	String codigoInscripcionIGJ = request.queryParams("codigoInscripcionIGJ");
    	
    	return null;
    }
}
