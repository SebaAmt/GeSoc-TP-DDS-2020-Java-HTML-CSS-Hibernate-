package dds.egreso;

import dds.direccion.Direccion;

public class Proveedor {

	private String nombre;
	private Integer dni;
	private Direccion direccion;

	
	public Proveedor(String nombre, Integer dni, Direccion direccion) {
		this.nombre = nombre;
		this.dni = dni;
		this.direccion = direccion;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	
	public Integer getDni() {
		return dni;
	}
	
	public Direccion getDireccion() {
		return direccion;
	}

}
