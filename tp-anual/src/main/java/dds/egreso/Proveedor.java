package dds.egreso;

import dds.pais.DireccionPostal;

public class Proveedor {

	private String nombre;
	private Integer dni;
	private DireccionPostal direccionPostal;

	
	public Proveedor(String nombre, Integer dni, DireccionPostal direccionPostal) {
		this.nombre = nombre;
		this.dni = dni;
		this.direccionPostal = direccionPostal;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	
	public Integer getDni() {
		return dni;
	}
	
	public DireccionPostal getDireccionPostal() {
		return direccionPostal;
	}

}
