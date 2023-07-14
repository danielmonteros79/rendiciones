package com.sa.decorator.delegacion;

import com.sa.decorator.SumTableDecorator;
import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import org.apache.commons.httpclient.util.DateUtil;

public class AbmDelegadoTableDecorator extends SumTableDecorator {
	protected String getVerLink() {
		return "";
	}

	protected String getEditarLink() {
		ParametriaUsuarioDelegado delegado = (ParametriaUsuarioDelegado) this.getCurrentRowObject();
		String imgTag = "<i class=\"bbva-icon icon-coronita_contract fa-lg\" data-toggle=\"tooltip\" title=\"Editar\"></i>";

		return "<a href=\"#a\" class=\"text-gray\" onclick=\"modalDelegadoShow('" + delegado.getId()
		+ "')\">" + imgTag + "</a>";
	}

	protected String getBorrarLink() {
		ParametriaUsuarioDelegado delegado = (ParametriaUsuarioDelegado) this.getCurrentRowObject();
		if (!delegado.getDelegadoEstado().equals("A")) {
			return "";
		} else {
			String imgTag = "<i class=\"bbva-icon icon-coronita_trash fa-lg\" data-toggle=\"tooltip\" title=\"Eliminar\"></i>";

			return "<a href=\"#a\" class=\"text-gray\" onclick=\"eliminarDelegado('" + delegado.getDelegadoUser()
			+ "', '" + DateUtil.formatDate(delegado.getFeDesde(), "yyyy-MM-dd") + "', '"
			+ DateUtil.formatDate(delegado.getFeHasta(), "yyyy-MM-dd") + "')\">" + imgTag + "</a>";
		}
	}

	@Override
	protected String getDestinatariosLink() {
		return null;
	}

	@Override
	protected String getCuponesLink() {
		return null;
	}

	@Override
	protected String getScanLink() {
		return null;
	}

	@Override
	protected String getCaratulaLink() {
		return null;
	}
}