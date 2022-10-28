package com.sa.decorator;
//comentario
import org.displaytag.decorator.TableDecorator;

public abstract class SumTableDecorator extends TableDecorator {

	protected static String SPACER = "&nbsp;";
	protected static String EMPTY_DIV = "<div></div>";
	protected static String EMPTY_SPAN = "<span>&nbsp;</span>";

	protected abstract String getVerLink();
	protected abstract String getEditarLink();
	protected abstract String getBorrarLink();
	protected abstract String getDestinatariosLink();
	protected abstract String getCuponesLink();
	protected abstract String getScanLink();
	protected abstract String getCaratulaLink();
	
	public String getOpciones() {
		String verLink = this.getVerLink();
		String editarLink = this.getEditarLink();
		String borrarLink = this.getBorrarLink();
		return verLink + SPACER + editarLink + SPACER + borrarLink;
	}
	
	public String getScan() {
		String verScan = this.getScanLink();
		return verScan;
	}
	public String getCaratula() {
		String car = this.getCaratulaLink();
		return car;
	}
	public String getComentarios() {
		String destinatariosLink = this.getDestinatariosLink();
		return destinatariosLink;
		}
	
	public String getCupones() {
	
		String cuponesLink = this.getCuponesLink();
		return cuponesLink;
	}
	
}
