package model.entidades;

import model.categoria.Categoria;
import model.reglaNegocio.ReglaNegocio;
import model.egreso.Egreso;
import model.egreso.EstadoEgreso;
import model.exception.ValidacionEgresoFallidaException;
import model.validacionesEgresos.ValidacionEgreso;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "organizaciones")
public class Organizacion {

	@Id
	@GeneratedValue
	private Long id;
    private String nombre;
    @OneToMany
	@JoinColumn(name = "organizacion_id")
    private List<Categoria> categorias = new ArrayList<>();
	@OneToMany
	@JoinColumn(name = "organizacion_id")
    private List<EntidadBase> entidadesBase = new ArrayList<>();
	@OneToMany
	@JoinColumn(name = "organizacion_id")
    private List<EntidadJuridica> entidadesJuridicas = new ArrayList<>();
    @ElementCollection
    private List<ValidacionEgreso> validacionesEgresos = new ArrayList<>();
	@ElementCollection
    private List<String> etiquetasDisponibles = new ArrayList<>();

	public Organizacion(String nombre) {
		this.nombre = nombre;
	}

	public Organizacion() {
	}
	
	public void agregarEntidadBase(EntidadBase nuevaEntidadBase) {
		this.entidadesBase.add(nuevaEntidadBase);
	}

	public void agregarEntidadJuridica(EntidadJuridica nuevaEntidadJuridica) {
		this.entidadesJuridicas.add(nuevaEntidadJuridica);
	}

	public void agregarValidacionEgreso(ValidacionEgreso nuevaValidacion) {
		this.validacionesEgresos.add(nuevaValidacion);
	}

	public void validarEgresos() {
		for (Egreso egresoPendiente : this.obtenerEgresosParaValidar()) {
			List<String> mensajesDeError = new ArrayList<>();
			this.validacionesEgresos.stream().forEach(validacion -> {
				try {
					validacion.validar(egresoPendiente);
				} catch (ValidacionEgresoFallidaException ex) {
					mensajesDeError.add(ex.getMessage());
				} catch (RuntimeException ex) {
					mensajesDeError.add("Error no controlado");
				}
			});

			if (mensajesDeError.size() == 0) {
				egresoPendiente.cambiarEstado(EstadoEgreso.ACEPTADO,
						"El " + egresoPendiente.toString() + " fue ACEPTADO");
			} else {
				egresoPendiente.cambiarEstado(EstadoEgreso.RECHAZADO, "El " + egresoPendiente.toString()
						+ " fue RECHAZADO: " + String.join(", ", mensajesDeError));
			}

		}
	}
	//TODO: Concatenar listas de entidades en el método obtenerEgresosParaValidar.
	public List<Egreso> obtenerEgresosParaValidar() {
		List<Egreso> egresosParaValidar = new ArrayList<>();
		this.entidadesBase.stream().forEach(entidad -> egresosParaValidar.addAll(entidad.egresosParaValidar()));
		this.entidadesJuridicas.stream().forEach(entidad -> egresosParaValidar.addAll(entidad.egresosParaValidar()));
		return egresosParaValidar;
	}

    @Override
    public String toString() {
        return "Organizacion: " + this.nombre;
    }
    
    public void crearCategoria(String nombreCategoria, List<ReglaNegocio> reglasNegocio) {
    	if (!categorias.stream().anyMatch(c->c.getNombreCategoria().equals(nombreCategoria))) {
    		this.categorias.add(new Categoria(nombreCategoria, reglasNegocio));
    	}
    }
    
    public void agregarCategoria(Categoria categoria) {
    	this.getCategorias().add(categoria);
    }
    
    public void agregarReglaNegocio(String nombreCategoria, ReglaNegocio reglaNegocio) {
    	this.getCategoria(nombreCategoria).agregarReglaNegocio(reglaNegocio);
    }
    
    public void eliminarReglaNegocio(String nombreCategoria, ReglaNegocio reglaNegocio) {
    	this.getCategoria(nombreCategoria).eliminarReglaNegocio(reglaNegocio);
    }
    
    public void eliminarCategoria(String nombreCategoria) {
    	this.categorias.remove(this.getCategoria(nombreCategoria));
    }
    
    public Categoria getCategoria(String nombreCategoria) {
    	return categorias.stream().filter(c->c.getNombreCategoria().equals(nombreCategoria)).collect(Collectors.toList()).get(0);
    }
    
    public void asignarCategoria(String nombreCategoria, Entidad entidad) {
    	entidad.setCategoria(this.getCategoria(nombreCategoria));
    }
    
	public String getNombre() {
		return this.nombre;
	}

	public List<String> getEtiquetasDisponibles() {
		return etiquetasDisponibles;
	}

	public void nuevaEtiqueta(String nuevaEtiqueta){
		this.etiquetasDisponibles.add(nuevaEtiqueta);
	}

	public void eliminarEtiqueta(String etiquetaAEliminar){
		this.etiquetasDisponibles.removeIf(etiqueta -> etiqueta.equals(etiquetaAEliminar));
	}

	public Long getId() {
		return id;
	}

	public List<EntidadBase> getEntidadesBase() {
		return entidadesBase;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public List<EntidadJuridica> getEntidadesJuridicas() {
		return entidadesJuridicas;
	}

	public List<ValidacionEgreso> getValidacionesEgresos() {
		return validacionesEgresos;
	}

}
