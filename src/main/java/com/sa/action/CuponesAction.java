package com.sa.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.entities.Cupones;
import com.sa.entities.Gastos;
import com.sa.services.PagosService;
import com.sa.services.RendicionesService;
import com.sa.services.trxs.SU56;
import com.sa.util.DateUtil;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class CuponesAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String action = request.getParameter("action") == null ? "" : request.getParameter("action");

			if (action.equals("consulta"))
				return this.consulta(samClient, request, response);
			else if (action.equals("asignar"))
				return this.asignar(samClient, request, response);

			return null;
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}

	private ActionForward consulta(SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		List<Cupones> cupones = new ArrayList<Cupones>();
		PagosService service = new PagosService(samClient);

		String idRendicion = request.getParameter("idRendicion");
		String idGasto = request.getParameter("idGasto");
		String codMotivo = request.getParameter("codMotivo");
		String montoMin = request.getParameter("montoMin");
		String moneda = request.getParameter("moneda");

		if (!request.getParameter("fechaDesde").equals("")) {
			String fechaDesde = DateUtil.dfYYYYMMDD.format(DateUtil.dfDDMMYYYY.parse(request.getParameter("fechaDesde")));
			String fechaHasta = DateUtil.dfYYYYMMDD.format(DateUtil.dfDDMMYYYY.parse(request.getParameter("fechaHasta")));

			cupones = service.getCupones("USU", "MOP", "SU", this.getSessionUserWorking().getIdUser(), fechaDesde, fechaHasta, idRendicion, codMotivo, montoMin, moneda);
		} else
			cupones = service.getCuponUnico(idRendicion, idGasto, this.getSessionUserWorking().getIdUser(), codMotivo);

		resp.put("filas", cupones);
		resp.put("message", service.getMsg());

		return writeJson(response, resp);
	}

	private ActionForward asignar(SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		PagosService pagosService = new PagosService(samClient);
		RendicionesService rendicionesService = new RendicionesService(samClient);

		String idRendicion = request.getParameter("idRendicion");
		String idGasto = request.getParameter("idGasto");
		String codMotivo = request.getParameter("codMotivo");
		String nroTarjeta = request.getParameter("nroTarjeta");
		String cupon = request.getParameter("cupon");
		String cupDeb = request.getParameter("cupDeb");
		String cupCred = request.getParameter("cupCred");
		String descCupon = request.getParameter("descCupon");
		String fechaPresentacion = request.getParameter("fechaPresentacion");

		List<Gastos> gastos = rendicionesService.getGastos(idRendicion, idGasto, this.getSessionUserWorking().getIdUser(), codMotivo);
		if (gastos.size() == 0)
			return writeError(response, "Gasto inexistente");

		Gastos gasto = gastos.get(0);

		pagosService.asignarCupon(SU56.OPCION_MODIFICAR, idRendicion, idGasto, this.getSessionUserWorking().getIdUser(), gasto.getMonto(), nroTarjeta, cupon, cupDeb,
				cupCred, descCupon, gasto.getMoneda(), fechaPresentacion);
		resp.put("message", pagosService.getMsg());

		return writeJson(response, resp);
	}
}
