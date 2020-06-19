package dds.egreso;

import com.sun.jersey.api.client.Client;
import dds.meLiApi.MeLiApi;
import dds.pais.DireccionPostal;
import dds.pais.Pais;
import dds.pais.Provincia;
import dds.pais.Ciudad;
import dds.pais.Barrio;
import dds.pais.Moneda;

public class CreadorProveedor {

	private MeLiApi meLiApi;
	
	public CreadorProveedor() {
		meLiApi = new MeLiApi(Client.create());
	}
	
	
	public Proveedor crearProveedor(String nombre, Integer dni, String idState, String idCity, String idNeighborhood, String calle, Integer altura, Integer piso, String departamento, String codigoPostal) {
		Pais pais = meLiApi.obtenerPais("AR");
		Provincia provincia = meLiApi.obtenerProvincia(idState);
		Ciudad ciudad = meLiApi.obtenerCiudad(idCity);
		Barrio barrio = meLiApi.obtenerBarrio(idNeighborhood);
		Moneda moneda = meLiApi.obtenerMoneda("ARS");
		pais.setMoneda(moneda);
		DireccionPostal direccion = new DireccionPostal(calle, altura, piso, departamento, codigoPostal, pais, provincia, ciudad, barrio);
		return new Proveedor(nombre, dni, direccion);
	}
}
