package dds.pais;

public class DireccionPostal {

	private String calle;
	private Integer altura;
	private Integer piso;
	private String departamento;
	private String codigoPostal;
	private Pais pais;
	private Provincia provincia;
	private Ciudad ciudad;
	private Barrio barrio;
	
	
	public DireccionPostal(String calle, Integer altura, Integer piso, String departamento, String codigoPostal, Pais pais, Provincia provincia, Ciudad ciudad, Barrio barrio) {
		this.calle = calle;
		this.altura = altura;
		this.piso = piso;
		this.departamento = departamento;
		this.codigoPostal = codigoPostal;
		this.pais = pais;
		this.provincia = provincia;
		this.ciudad = ciudad;
		this.barrio = barrio;
	}
	
	
	public String getCalle() {
		return calle;
	}
	
	public Integer getAltura() {
		return altura;
	}
	
	public Integer getPiso() {
		return piso;
	}
	
	public String getDepartamento() {
		return departamento;
	}
	
	public String getCodigoPostal() {
		return codigoPostal;
	}
	
	public Pais getPais() {
		return pais;
	}
	
	public Provincia getProvincia() {
		return provincia;
	}
	
	public Ciudad getCiudad() {
		return ciudad;
	}
	
	public Barrio getBarrio() {
		return barrio;
	}
}
