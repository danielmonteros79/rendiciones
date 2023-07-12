package com.sa.decorator.alertas;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.sa.decorator.SumTableDecorator;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;

public class DetalleAlertaTableDecorator extends SumTableDecorator {
	
	@Override
	protected String getVerLink() {
		return "";
	}

	@Override
	protected String getBorrarLink() {
		return "";
	}

	@Override
	protected String getEditarLink() {
		return "";
	}

	protected String getThubanLink() {
		return "";
	}

	protected String getJournalLink() {
		return "";
	}
	
	
	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	public String getAlerta() {
		Gastos gasto = (Gastos) this.getCurrentRowObject();
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String nroAdea = "";
		List<Rendicion> rendiciones = (List<Rendicion>) request.getAttribute("rendicionesData");
		for (Rendicion rendicion : rendiciones) {
			if(gasto.getIdRendicion().equals(rendicion.getId())) {
				nroAdea = rendicion.getAdea();
			}
		}
		detallarAlerta(nroAdea,gasto );
		return gasto.getAlerta();
		
	}
	
	private void detallarAlerta(String nroAdea, Gastos gasto) {
		if(gasto.getIdGasto().equals("2")) {
			gasto.setAlerta("EL GASTO NRO: " +  gasto.getNroGasto() + " SUPERA EL MONTO MENSUAL (10) SE INFORMARON 34");
		}
		if(nroAdea.substring(0).contains("01")) {
			gasto.setAlerta("EL GASTO NRO: " + gasto.getNroGasto() + "SUPERA LA CANTIDAD MENSUAL (1) SE INFORMARON 2");
		}
	}
	
	public String getIdRendicion() {
		Gastos gasto = (Gastos) this.getCurrentRowObject();
		return gasto.getIdRendicion().substring(gasto.getIdRendicion().length() - 4);
	}
	
	@Override
	public String getCupones() {
		Rendicion rend = (Rendicion) this.getCurrentRowObject();
		String gasto = "";
		String nroGasto = "";
		rend.getGastosRendicion();

		return rend.getCodMotivo();
	}
	
	@Override
	protected String getDestinatariosLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getScan() {
		return "";
	}
	@Override
	public String getCaratula() {
		return "";
	}
	@Override
	public String getComentarios() {
		return "";
		}
	

	@Override
	protected String getScanLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getCaratulaLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getCuponesLink() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

	