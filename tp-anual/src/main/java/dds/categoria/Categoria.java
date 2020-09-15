package dds.categoria;

import java.util.ArrayList;
import java.util.List;
import dds.reglaNegocio.ReglaNegocio;
import dds.egreso.Egreso;
import dds.entidades.Entidad;
import dds.entidades.EntidadBase;
import dds.entidades.EntidadJuridica;

public class Categoria {

	private TipoCategoria tipoCategoria;
	private List<ReglaNegocio> reglaNegocios = new ArrayList<>();
	
	
	public Categoria(TipoCategoria tipoCategoria, List<ReglaNegocio> reglaNegocios) {
		this.tipoCategoria = tipoCategoria;
		this.reglaNegocios = reglaNegocios;
	}
	
	
	public void nuevoEgreso(Entidad entidad, Egreso egreso) {
		reglaNegocios.stream().forEach(c->c.nuevoEgreso(entidad, egreso));
	}

	public void agregarEntidadBase(EntidadJuridica entidad) {
		reglaNegocios.stream().forEach(c->c.agregarEntidadBase(entidad));
	}

	public void puedeAgregarse(EntidadBase entidad) {
		reglaNegocios.stream().forEachOrdered(c->c.puedeAgregarse(entidad));
	}
	
	public void agregarReglaNegocio(ReglaNegocio reglaNegocio) {
		this.reglaNegocios.add(reglaNegocio);
	}
	
	public void eliminarReglaNegocio(ReglaNegocio reglaNegocio) {
		this.reglaNegocios.remove(reglaNegocio);
	}
	
	public TipoCategoria getTipoCategoria() {
		return tipoCategoria;
	}

	public List<ReglaNegocio> getReglaNegocios() {
		return reglaNegocios;
	}
}
