package dds.categoria;

import java.util.ArrayList;
import java.util.List;
import dds.comportamiento.Comportamiento;
import dds.egreso.Egreso;
import dds.entidades.Entidad;
import dds.entidades.EntidadBase;
import dds.entidades.EntidadJuridica;

public class Categoria {

	private TipoCategoria tipoCategoria;
	private List<Comportamiento> comportamientos = new ArrayList<>();
	
	
	public Categoria(TipoCategoria tipoCategoria, List<Comportamiento> comportamientos) {
		this.tipoCategoria = tipoCategoria;
		this.comportamientos = comportamientos;
	}
	
	
	public void nuevoEgreso(Entidad entidad, Egreso egreso) {
		comportamientos.stream().forEach(c->c.nuevoEgreso(entidad, egreso));
	}

	public void agregarEntidadBase(EntidadJuridica entidad) {
		comportamientos.stream().forEach(c->c.agregarEntidadBase(entidad));
	}

	public void puedeAgregarse(EntidadBase entidad) {
		comportamientos.stream().forEachOrdered(c->c.puedeAgregarse(entidad));
	}
	
	public void agregarComportamiento(Comportamiento comportamiento) {
		this.comportamientos.add(comportamiento);
	}
	
	public void eliminarComportamiento(Comportamiento comportamiento) {
		this.comportamientos.remove(comportamiento);		
	}
	
	public TipoCategoria getTipoCategoria() {
		return tipoCategoria;
	}

	public List<Comportamiento> getComportamientos() {
		return comportamientos;
	}
}
