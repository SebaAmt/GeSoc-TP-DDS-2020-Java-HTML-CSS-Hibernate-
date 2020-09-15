package dds.entidades;

import dds.categoria.Categoria;
import dds.categoria.TipoCategoria;
import dds.comportamiento.Comportamiento;
import dds.egreso.Egreso;
import dds.egreso.EstadoEgreso;
import dds.exception.ValidacionEgresoFallidaException;
import dds.validacionesEgresos.ValidacionEgreso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Organizacion {

    private String nombre;
    private List<Categoria> categorias = new ArrayList<>();
    private List<EntidadBase> entidadesBase = new ArrayList<>();
    private List<EntidadJuridica> entidadesJuridicas = new ArrayList<>();
    private List<ValidacionEgreso> validacionesEgresos = new ArrayList<>();
    private List<String> etiquetasDisponibles = new ArrayList<>();

	public Organizacion(String nombre) {
		this.nombre = nombre;
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
						"El Egreso " + egresoPendiente.toString() + " fue ACEPTADO");
			} else {
				egresoPendiente.cambiarEstado(EstadoEgreso.RECHAZADO, "El Egreso " + egresoPendiente.toString()
						+ " fue RECHAZADO: " + String.join(", ", mensajesDeError));
			}

		}
	}

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
    
    public void crearCategoria(TipoCategoria tipoCategoria, List<Comportamiento> comportamientos) {
    	if (!categorias.stream().anyMatch(c->c.getTipoCategoria().equals(tipoCategoria))) {
    		this.categorias.add(new Categoria(tipoCategoria, comportamientos));
    	}
    }
    
    public void agregarComportamiento(TipoCategoria tipoCategoria, Comportamiento comportamiento) {
    	this.getCategoria(tipoCategoria).agregarComportamiento(comportamiento);
    }
    
    public void eliminarComportamiento(TipoCategoria tipoCategoria, Comportamiento comportamiento) {
    	this.getCategoria(tipoCategoria).eliminarComportamiento(comportamiento);
    }
    
    public void eliminarCategoria(TipoCategoria tipoCategoria) {
    	this.categorias.remove(this.getCategoria(tipoCategoria));
    }
    
    public Categoria getCategoria(TipoCategoria tipoCategoria) {
    	return categorias.stream().filter(c->c.getTipoCategoria().equals(tipoCategoria)).collect(Collectors.toList()).get(0);
    }
    
    public void asignarCategoria(TipoCategoria tipoCategoria, Entidad entidad) {
    	entidad.setCategoria(this.getCategoria(tipoCategoria));
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

}
