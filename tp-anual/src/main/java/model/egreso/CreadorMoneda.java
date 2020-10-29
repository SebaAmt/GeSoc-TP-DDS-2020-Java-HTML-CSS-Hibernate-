package model.egreso;

import model.meLiApi.MeLiApi;

public class CreadorMoneda {

	private CurrencyID currencyId;
	
	public CreadorMoneda(CurrencyID currencyId) {
		this.currencyId = currencyId;
	}
	
	public Moneda getMoneda() {
		return MeLiApi.obtenerMoneda(currencyId.toString());
	}
}
