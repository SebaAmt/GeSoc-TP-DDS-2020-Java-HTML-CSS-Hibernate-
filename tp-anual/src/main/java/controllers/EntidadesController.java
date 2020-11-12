package controllers;

import model.categoria.Categoria;
import model.entidades.Entidad;
import model.entidades.EntidadBase;
import model.entidades.EntidadJuridica;
import model.entidades.Organizacion;

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

	
	public ModelAndView getFormCreacionEntidadBase(Request request, Response response) {
		return this.getFormCreacionEntidad(request, response, "form-creacion-entidad-base.html.hbs");
	}
	
	public ModelAndView getFormCreacionEntidadJuridica(Request request, Response response) {
		return this.getFormCreacionEntidad(request, response, "form-creacion-entidad-juridica.html.hbs");
	}
	
	private ModelAndView getFormCreacionEntidad(Request request, Response response, String form) {
        String idOrg = request.params(":idOrg");
        try{
            SessionHelper.validarOrganizacionUsuarioLogueado(request, response, Long.parseLong(idOrg));

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("idOrganizacion", Long.parseLong(idOrg));
            modelo.put("categorias", RepositorioOrganizaciones.instancia.obtenerCategoriasDeOrganizacion((Long.parseLong(idOrg))));
            return new ModelAndView(modelo, form);
        }
        catch (Exception ex){
            response.status(400);
            return null;
        }
    }
	
    public ModelAndView createEntidadBase(Request request, Response response){
      	String nombre = request.queryParams("nombre");
      	String descripcion = request.queryParams("descripcion");
    	
    	EntidadBase nuevaEntidad = new EntidadBase(nombre, descripcion);
    	
        withTransaction(() ->{
        	if(request.queryParams("categoria") != null) {
        		Categoria categoria = RepositorioCategorias.instancia.getCategoriaPorId(Long.parseLong(request.queryParams("categoria")));
        		RepositorioCategorias.instancia.agregarCategoria(categoria);
            	nuevaEntidad.setCategoria(categoria);
            }
        	RepositorioEntidades.instancia.agregarEntidad(nuevaEntidad);
        	Organizacion organizacion = RepositorioOrganizaciones.instancia.obtenerOrganizacionPorId(Long.parseLong(request.params(":idOrg")));
        	organizacion.agregarEntidadBase(nuevaEntidad);
        });
        response.redirect("/organizaciones/" + request.params(":idOrg") + "/entidades/base/" + nuevaEntidad.getId());
    	return null;    	
    }

    public ModelAndView createEntidadJuridica(Request request, Response response){
    	String nombre = request.queryParams("nombre");
    	String razonSocial = request.queryParams("nombre");
    	String cuit = request.queryParams("nombre");
    	String codigoPostal = request.queryParams("codigoPostal");
    	String codigoInscripcionIGJ = request.queryParams("codigoInscripcionIGJ");
		
    	EntidadJuridica nuevaEntidad = new EntidadJuridica(nombre, razonSocial, cuit, codigoPostal, codigoInscripcionIGJ);
		
    	withTransaction(() ->{
        	if(request.queryParams("categoria") != null) {
        		Categoria categoria = RepositorioCategorias.instancia.getCategoriaPorId(Long.parseLong(request.queryParams("categoria")));
        		RepositorioCategorias.instancia.agregarCategoria(categoria);
        		nuevaEntidad.setCategoria(categoria);
            }
        	RepositorioEntidades.instancia.agregarEntidad(nuevaEntidad);        	
        	Organizacion organizacion = RepositorioOrganizaciones.instancia.obtenerOrganizacionPorId(Long.parseLong(request.params(":idOrg")));
        	organizacion.agregarEntidadJuridica(nuevaEntidad);
        });
    	response.redirect("/organizaciones/" + request.params(":idOrg") + "/entidades/juridica/" + nuevaEntidad.getId());
    	return null;
	}
        
	public ModelAndView getDetalleEntidadBase(Request request, Response response){
		return this.getDetalleEntidad(request, response, "detalle-entidad-base.html.hbs");
	}

	public ModelAndView getDetalleEntidadJuridica(Request request, Response response){
		return this.getDetalleEntidad(request, response, "detalle-entidad-juridica.html.hbs");
	}
	
    private ModelAndView getDetalleEntidad(Request request, Response response, String form){
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
            modelo.put("categorias", RepositorioOrganizaciones.instancia.obtenerCategoriasDeOrganizacion(Long.parseLong(idOrg)));
            modelo.put("idOrganizacion",Long.parseLong(idOrg));
            
            return new ModelAndView(modelo, form);
        }
        catch (Exception ex){
            response.status(400);
            return null;
        }
    }    
    
    public Void modificarCategoria(Request request, Response response) {
        String idOrg = request.params(":idOrg");
        String tipoEntidad = request.params("tipoEntidad");
        String idEntidad = request.params(":idEntidad");
        Entidad entidad = RepositorioEntidades.instancia.obtenerEntidadPorId(Long.parseLong(idEntidad));

        withTransaction(() -> {
        	Long idCategoriaAAsignar = Long.parseLong(request.queryParams("nuevaCategoriaAsignada"));
        	entidad.setCategoria(RepositorioCategorias.instancia.getCategoriaPorId(idCategoriaAAsignar));
        });

    	response.redirect("/organizaciones/" + idOrg + "/entidades/" + tipoEntidad + "/" + idEntidad);
        return null;
    }
}
