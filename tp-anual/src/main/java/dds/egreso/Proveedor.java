package dds.egreso;
public class Proveedor {

	private String nombre;
	private Integer dni;
	private String direccionPostal;

	
	public Proveedor(String nombre, Integer dni, String direccionPostal) {
		setNombre(nombre);
		setDni(dni);
		setDireccionPostal(direccionPostal);
	}
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getDni() {
		return dni;
	}
	public void setDni(Integer dni) {
		this.dni = dni;
	}
	public String getDireccionPostal() {
		return direccionPostal;
	}
	public void setDireccionPostal(String direccionPostal) {
		this.direccionPostal = direccionPostal;
	}

}
