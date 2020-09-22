package dds.reglaNegocio;


import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import dds.egreso.Egreso;
import dds.entidades.Entidad;
import dds.entidades.EntidadBase;
import dds.entidades.EntidadJuridica;

@Entity 
@Table (name = "Reglas")
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo_regla")
public abstract class ReglaNegocio {
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE)
	private Long id;
	
	public void nuevoEgreso(Entidad entidad, Egreso egreso) {}
	public void agregarEntidadBase(EntidadJuridica entidad) {}
	public void puedeAgregarse(EntidadBase entidad) {}
}
